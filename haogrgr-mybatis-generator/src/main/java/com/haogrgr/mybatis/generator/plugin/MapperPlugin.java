package com.haogrgr.mybatis.generator.plugin;

import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.haogrgr.mybatis.generator.methods.DelByIdsMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetAllCountMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetAllMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetByIdsMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetByPageCountMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetByPageListMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetByPageSqlMethodBuilder;
import com.haogrgr.mybatis.generator.methods.GetByPojoMethodBuilder;
import com.haogrgr.mybatis.generator.methods.SaveBatchMethodBuilder;
import com.haogrgr.mybatis.generator.utils.PluginUtils;
import com.haogrgr.mybatis.generator.utils.Reflects;

/**
 * 生成Mapper.xml和Mapper.java的插件
 * 
 * @author desheng.tu
 * @date 2014年8月5日 下午6:41:07 
 *
 */
public class MapperPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	/**
	 * BaseMapper类全名
	 */
	private String getBaseMapperName() {
		return this.getProperties().getProperty("baseMapperName");
	}

	/**
	 * 要替换的字符, eg: 当model名为TestModel时, 生成的Mapper等为 TestModelMapper, 而我喜欢的是TestMapper, 所以需要替换掉Model
	 */
	private String getToBeReplace() {
		String property = this.getProperties().getProperty("toBeReplace");
		if (property == null) {
			return "";
		}
		return property;
	}

	/**
	 * 该方法在生成Mapper.java接口文件前调用
	 * 
	 * 因为我的项目所有的mapper都继承自BaseMapper,所以这里添加继承,然后去掉所有原来的方法
	 * 
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topClass, IntrospectedTable introspectedTable) {
		//为所有的mapper接口继承xxxx.BaseMapper
		String domainObjectName = PluginUtils.getModelName(introspectedTable);
		String primaryKeyType = PluginUtils.getPKTypeShortName(introspectedTable);
		String parentInterface = getBaseMapperName() + "<" + domainObjectName + ", " + primaryKeyType + ">";
		FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(parentInterface);
		interfaze.addImportedType(superInterface);
		interfaze.addSuperInterface(superInterface);

		//清楚其他方法,所有方法都放在BaseMapper中
		interfaze.getMethods().clear();

		//反射修改类名,去掉里面的指定的字符串
		FullyQualifiedJavaType type = interfaze.getType();
		PluginUtils.replace(type, "baseShortName", getToBeReplace(), "");
		PluginUtils.replace(type, "baseQualifiedName", getToBeReplace(), "");

		return super.clientGenerated(interfaze, topClass, introspectedTable);
	}

	/**
	 * 该方法在生成xml文件前调用
	 */
	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		//反射修改mapper.xml的文件名,去掉里面的Model字符串
		PluginUtils.replace(sqlMap, "fileName", getToBeReplace(), "");

		return super.sqlMapGenerated(sqlMap, introspectedTable);
	}

	/**
	 * 该方法在sqlMapGenerated方法前调用
	 * 对xml的修改可以在这里处理
	 * 
	 * 我这里的处理是:
	 * 1.去掉namespace中的Model字符串(你不一定需要)
	 * 2.改方法名(如:selectByPrimaryKey 改为 findById),或者删除不用的方法(如:insertSelective方法)
	 * 3.添加新方法(如:批量插入,批量删除,mysql分页查询等等)
	 */
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement mapper = document.getRootElement();

		//反射修改mapper.xml中namespace属性值,去掉里面的Model字符串
		Attribute namespace = mapper.getAttributes().get(0);
		PluginUtils.replace(namespace, "value", getToBeReplace(), "");

		//需要修改方法名或删除的方法
		updateSourceMethods(mapper);

		//添加新方法
		mapper.addElement(new GetByIdsMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new GetByPojoMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new GetAllMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new GetAllCountMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new GetByPageSqlMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new GetByPageListMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new GetByPageCountMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new SaveBatchMethodBuilder().with(introspectedTable).build());
		mapper.addElement(new DelByIdsMethodBuilder().with(introspectedTable).build());

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private void updateSourceMethods(XmlElement mapper) {
		Iterator<Element> eleIterator = mapper.getElements().iterator();
		while (eleIterator.hasNext()) {
			XmlElement xmlEle = (XmlElement) eleIterator.next();
			Attribute idAttr = getEleAttr(xmlEle, "id");

			if (idAttr.getValue().equals("selectByPrimaryKey")) {
				Reflects.setField(idAttr, "value", "getById");
			}
			else if (idAttr.getValue().equals("deleteByPrimaryKey")) {
				Reflects.setField(idAttr, "value", "delById");
			}
			else if (idAttr.getValue().equals("insert")) {
				Reflects.setField(idAttr, "value", "save");
			}
			else if (idAttr.getValue().equals("insertSelective")) {
				eleIterator.remove();
			}
			else if (idAttr.getValue().equals("updateByPrimaryKey")) {
				Reflects.setField(idAttr, "value", "modify");
			}
			else if (idAttr.getValue().equals("updateByPrimaryKeySelective")) {
				Reflects.setField(idAttr, "value", "modifySelective");
			}
		}
	}

	protected Attribute getEleAttr(XmlElement ele, String attrName) {
		for (Attribute attr : ele.getAttributes()) {
			if (attr.getName().equals(attrName)) {
				return attr;
			}
		}
		return null;
	}
}

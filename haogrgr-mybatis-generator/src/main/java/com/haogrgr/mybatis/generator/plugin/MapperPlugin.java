package com.haogrgr.mybatis.generator.plugin;

import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import com.haogrgr.mybatis.generator.utils.PluginUtils;
import com.haogrgr.mybatis.generator.utils.Reflects;

/**
 * mybatis-generator提供的插件接口,配置在genrator.xml中
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

	private String getBaseMapperName() {
		return this.getProperties().getProperty("baseMapperName");
	}

	private String getToBeReplace() {
		String property = this.getProperties().getProperty("toBeReplace");
		if (property == null) {
			return "";
		}
		return property;
	}

	/**
	 * 该方法在生成java接口文件前调用
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
		addAllMethod(mapper, introspectedTable, "all");
		addCountMethod(mapper, introspectedTable, "count");
		addInsertsMethod(mapper, introspectedTable, "inserts");
		addDeletesMethod(mapper, introspectedTable, "deletes");
		addLoadMethod(mapper, introspectedTable, "load");
		addFindByPageSql(mapper, introspectedTable, "findByPageSql");
		addFindByPageMethod(mapper, introspectedTable, "findByPage");
		addFindByPageCountMethod(mapper, introspectedTable, "findByPageCount");

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private void updateSourceMethods(XmlElement mapper) {
		Iterator<Element> eleIterator = mapper.getElements().iterator();
		while (eleIterator.hasNext()) {
			XmlElement xmlEle = (XmlElement) eleIterator.next();
			Attribute idAttr = getEleAttr(xmlEle, "id");

			if (idAttr.getValue().equals("selectByPrimaryKey")) {
				Reflects.setField(idAttr, "value", "findById");
			}
			else if (idAttr.getValue().equals("deleteByPrimaryKey")) {
				Reflects.setField(idAttr, "value", "delete");
			}
			else if (idAttr.getValue().equals("insertSelective")) {
				eleIterator.remove();
			}
			else if (idAttr.getValue().equals("updateByPrimaryKeySelective")) {
				Reflects.setField(idAttr, "value", "update");
			}
			else if (idAttr.getValue().equals("updateByPrimaryKey")) {
				eleIterator.remove();
			}
		}
	}

	protected void addFindByPageCountMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", id));
		ele.addAttribute(new Attribute("resultType", "java.lang.Integer"));
		ele.addAttribute(new Attribute("parameterType", "PageInfo"));

		ele.addElement(new TextElement("select count(1) from ("));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "findByPageSql"));
		ele.addElement(include);

		ele.addElement(new TextElement(") temp "));

		mapper.addElement(ele);
	}

	protected void addFindByPageMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", id));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		ele.addAttribute(new Attribute("parameterType", "PageInfo"));

		ele.addElement(new TextElement("select temp.* from ("));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "findByPageSql"));
		ele.addElement(include);

		ele.addElement(new TextElement(") temp limit #{offset}, #{pageSize}"));

		mapper.addElement(ele);
	}

	protected void addFindByPageSql(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("sql");
		ele.addAttribute(new Attribute("id", id));

		ele.addElement(new TextElement("select a.* from " + tableName(table) + " a where 1=1 "));

		Iterator<IntrospectedColumn> iter = table.getAllColumns().iterator();
		while (iter.hasNext()) {
			IntrospectedColumn column = iter.next();
			if (column.getActualColumnName().equals("create_time")
					|| column.getActualColumnName().equals("modify_time")) {
				continue;
			}

			XmlElement iftag = new XmlElement("if");
			iftag.addAttribute(new Attribute("test", column.getJavaProperty("params.") + " != null"));

			String columnStr = "a." + MyBatis3FormattingUtilities.getEscapedColumnName(column);
			String paramStr = MyBatis3FormattingUtilities.getParameterClause(column, "params.");
			iftag.addElement(new TextElement("and " + columnStr + " = " + paramStr));

			ele.addElement(iftag);
		}

		ele.addElement(new TextElement("order by a." + pkname(table) + " desc"));

		mapper.addElement(ele);
	}

	protected void addLoadMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", id));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));

		if (table.getRules().generatePrimaryKeyClass()) {
			System.err.println("loads : " + tableName(table) + " has complex primary key.");
			ele.addElement(new TextElement("unsupport method"));
			mapper.addElement(ele);
			return;
		}

		ele.addElement(new TextElement("select "));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "Base_Column_List"));
		ele.addElement(include);

		ele.addElement(new TextElement("from " + tableName(table) + " where " + pkname(table) + " in "));

		XmlElement foreach = new XmlElement("foreach");
		foreach.addAttribute(new Attribute("collection", "pks"));
		foreach.addAttribute(new Attribute("item", "item"));
		foreach.addAttribute(new Attribute("open", "("));
		foreach.addAttribute(new Attribute("separator", ","));
		foreach.addAttribute(new Attribute("close", ")"));
		foreach.addElement(new TextElement("#{item}"));

		ele.addElement(foreach);

		mapper.addElement(ele);
	}

	protected void addDeletesMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("delete");
		ele.addAttribute(new Attribute("id", id));

		//多主键, 不能批量删除, 留空
		if (table.getRules().generatePrimaryKeyClass()) {
			System.err.println("deletes : " + tableName(table) + " has complex primary key.");
			ele.addElement(new TextElement("unsupport method"));
			mapper.addElement(ele);
			return;
		}

		ele.addElement(new TextElement("delete from " + tableName(table) + " where " + pkname(table) + " in "));

		XmlElement foreach = new XmlElement("foreach");
		foreach.addAttribute(new Attribute("collection", "pks"));
		foreach.addAttribute(new Attribute("item", "item"));
		foreach.addAttribute(new Attribute("open", "("));
		foreach.addAttribute(new Attribute("separator", ","));
		foreach.addAttribute(new Attribute("close", ")"));
		foreach.addElement(new TextElement("#{item}"));

		ele.addElement(foreach);

		mapper.addElement(ele);
	}

	protected void addInsertsMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("insert");
		ele.addAttribute(new Attribute("id", id));
		ele.addAttribute(new Attribute("parameterType", "java.util.List"));

		StringBuilder insertClause = new StringBuilder("insert into ");
		insertClause.append(tableName(table) + " (");

		StringBuilder valuesClause = new StringBuilder("(");

		Iterator<IntrospectedColumn> iter = table.getAllColumns().iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			if (introspectedColumn.isIdentity()) {
				continue;
			}

			insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
			if (iter.hasNext()) {
				insertClause.append(", ");
				valuesClause.append(", ");
			}
		}

		ele.addElement(new TextElement(insertClause.append(")").toString()));
		ele.addElement(new TextElement("values"));

		XmlElement foreach = new XmlElement("foreach");
		foreach.addAttribute(new Attribute("collection", "records"));
		foreach.addAttribute(new Attribute("item", "item"));
		foreach.addAttribute(new Attribute("separator", ","));
		foreach.addElement(new TextElement(valuesClause.append(")").toString()));

		ele.addElement(foreach);

		mapper.addElement(ele);
	}

	protected void addCountMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", id));
		ele.addAttribute(new Attribute("resultType", "java.lang.Integer"));

		ele.addElement(new TextElement("select count(1) from " + tableName(table)));

		mapper.addElement(ele);
	}

	protected void addAllMethod(XmlElement mapper, IntrospectedTable table, String id) {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", id));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));

		ele.addElement(new TextElement("select "));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "Base_Column_List"));
		ele.addElement(include);

		ele.addElement(new TextElement("from " + tableName(table)));

		mapper.addElement(ele);
	}

	protected Attribute getEleAttr(XmlElement ele, String attrName) {
		for (Attribute attr : ele.getAttributes()) {
			if (attr.getName().equals(attrName)) {
				return attr;
			}
		}
		return null;
	}

	private String tableName(IntrospectedTable table) {
		return table.getFullyQualifiedTableNameAtRuntime();
	}

	private String pkname(IntrospectedTable table) {
		return MyBatis3FormattingUtilities.getEscapedColumnName(table.getPrimaryKeyColumns().get(0));
	}
}

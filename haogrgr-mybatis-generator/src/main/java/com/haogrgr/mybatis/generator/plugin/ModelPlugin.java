package com.haogrgr.mybatis.generator.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.google.common.base.Strings;

/**
 * 生成Model插件, 添加主键的构造方法, 实现Serializable接口, 字段加注释
 * 
 * @author desheng.tu
 * @date 2015年12月2日 下午7:39:35 
 *
 */
public class ModelPlugin extends PluginAdapter implements Plugin {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	private boolean getGenerateSerialVersionField() {
		String property = this.getProperties().getProperty("generateSerialVersionField");
		return "true".equals(property);
	}

	private String getToBeReplace() {
		String property = this.getProperties().getProperty("toBeReplace");
		if (property == null) {
			return "";
		}
		return property;
	}

	/**
	 * 替换掉主键类中的指定字符, 如   TestModelKey -> TestKey
	 */
	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		String pkname = introspectedTable.getPrimaryKeyType();
		pkname = pkname.replace(getToBeReplace(), "");
		introspectedTable.setPrimaryKeyType(pkname);
	}

	/**
	 * 主键类生成, 联合主键会生成主键类
	 */
	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//添加serialVersionUID属性
		addSerialVersionField(topLevelClass);

		//添加设置主键的构造方法
		addPkConstructorMethod(topLevelClass, introspectedTable, false);

		//添加默认构造方法
		addDefaultConstructorMethod(topLevelClass);

		//字段加注释
		addFieldRemarks(topLevelClass, introspectedTable);

		return true;
	}

	/**
	 * 该方法在生成model文件前调用
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//添加serialVersionUID属性
		addSerialVersionField(topLevelClass);

		//添加设置主键的构造方法
		addPkConstructorMethod(topLevelClass, introspectedTable, introspectedTable.getRules().generatePrimaryKeyClass());

		//添加默认构造方法
		addDefaultConstructorMethod(topLevelClass);

		//字段加注释
		addFieldRemarks(topLevelClass, introspectedTable);

		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	private void addFieldRemarks(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Map<String, String> map = new HashMap<>();
		for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
			map.put(column.getJavaProperty(), column.getActualColumnName() + " : " + column.getRemarks());
		}
		for (Field field : topLevelClass.getFields()) {
			String c = map.get(field.getName());
			if (!Strings.isNullOrEmpty(c)) {
				field.addJavaDocLine("/** " + c + "**/");
			}
		}
	}

	private void addSerialVersionField(TopLevelClass topLevelClass) {
		if (!getGenerateSerialVersionField()) {
			return;
		}

		topLevelClass.addImportedType("java.io.Serializable");
		topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));

		Field serial = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
		serial.setStatic(true);
		serial.setFinal(true);
		serial.setVisibility(JavaVisibility.PRIVATE);
		serial.setInitializationString("-1L");
		topLevelClass.getFields().add(0, serial);
	}

	private void addDefaultConstructorMethod(TopLevelClass topLevelClass) {
		Method dcmethod = new Method(topLevelClass.getType().getShortName());
		dcmethod.setConstructor(true);
		dcmethod.setVisibility(JavaVisibility.PUBLIC);
		dcmethod.addBodyLine("");
		topLevelClass.getMethods().add(0, dcmethod);
	}

	private void addPkConstructorMethod(TopLevelClass topLevelClass, IntrospectedTable table, boolean callSuper) {
		Method cmethod = new Method(topLevelClass.getType().getShortName());
		cmethod.setVisibility(JavaVisibility.PUBLIC);
		cmethod.setConstructor(true);
		for (IntrospectedColumn pkclumn : table.getPrimaryKeyColumns()) {
			cmethod.addParameter(new Parameter(pkclumn.getFullyQualifiedJavaType(), pkclumn.getJavaProperty()));
		}

		if (!callSuper) {
			table.getPrimaryKeyColumns().stream().forEach(pkclumn -> {
				cmethod.addBodyLine("this." + pkclumn.getJavaProperty() + " = " + pkclumn.getJavaProperty() + ";");
			});
		}
		else {//有主键类, 直接调用super构造方法
			table.getPrimaryKeyColumns().stream().map(pkclumn -> pkclumn.getJavaProperty())
					.reduce((pk1, pk2) -> pk1 + ", " + pk2)
					.ifPresent(pkstr -> cmethod.addBodyLine("super(" + pkstr + ");"));
		}

		topLevelClass.getMethods().add(0, cmethod);
	}

}

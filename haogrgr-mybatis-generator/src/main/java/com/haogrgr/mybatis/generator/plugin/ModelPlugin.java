package com.haogrgr.mybatis.generator.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.haogrgr.mybatis.generator.utils.DBUtils;

/**
 * mybatis-generator提供的插件接口,配置在genrator.xml中
 * <p>Author: desheng.tu</p>
 * <p>Date: 2014年8月5日</p>
 */
public class ModelPlugin extends PluginAdapter implements Plugin {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	/**
	 * 该方法在生成model文件前调用
	 * 
	 * 因为我的项目所有model都继承自BaseModel,所以这里添加继承,然后去掉积累中以有的属性(id, create_time, modify_tiem)
	 * 
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//所有model继承自BaseModel
		FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(topLevelClass.getType().getPackageName()
				+ ".BaseModel");
		topLevelClass.addImportedType(superInterface);
		topLevelClass.setSuperClass(superInterface);

		//添加serialVersionUID属性
		FullyQualifiedJavaType longClazz = new FullyQualifiedJavaType("long");
		org.mybatis.generator.api.dom.java.Field serial = new org.mybatis.generator.api.dom.java.Field(
				"serialVersionUID", longClazz);
		serial.setStatic(true);
		serial.setFinal(true);
		serial.setVisibility(JavaVisibility.PRIVATE);
		serial.setInitializationString("-1L");
		topLevelClass.getFields().add(0, serial);

		//移除继承自BaseModel中的属性
		org.mybatis.generator.api.dom.java.Field pkField = null;
		List<org.mybatis.generator.api.dom.java.Field> fields = topLevelClass.getFields();
		List<org.mybatis.generator.api.dom.java.Field> toRemoveFields = new ArrayList<>();
		for (org.mybatis.generator.api.dom.java.Field field : fields) {
			if (field.getName().equals("id")) {
				toRemoveFields.add(field);
				pkField = field;
			}
			else if (field.getName().equals("createTime")) {
				toRemoveFields.add(field);
			}
			else if (field.getName().equals("modifyTime")) {
				toRemoveFields.add(field);
			}
		}
		System.out.println(toRemoveFields);
		fields.removeAll(toRemoveFields);

		//移除继承自BaseModel中的方法
		List<Method> methods = topLevelClass.getMethods();
		List<Method> toRemoveMethods = new ArrayList<>();
		for (Method method : methods) {
			if (method.getName().equals("getId") || method.getName().equals("setId")) {
				toRemoveMethods.add(method);
			}
			else if (method.getName().equals("getCreateTime") || method.getName().equals("setCreateTime")) {
				toRemoveMethods.add(method);
			}
			else if (method.getName().equals("getModifyTime") || method.getName().equals("setModifyTime")) {
				toRemoveMethods.add(method);
			}
		}
		System.out.println(toRemoveFields);
		methods.removeAll(toRemoveMethods);

		//添加设置id的构造方法
		Method cmethod = new Method(topLevelClass.getType().getShortName());
		cmethod.setConstructor(true);
		cmethod.addParameter(new Parameter(pkField.getType(), pkField.getName()));
		cmethod.setVisibility(JavaVisibility.PUBLIC);
		cmethod.addBodyLine("this." + pkField.getName() + " = " + pkField.getName() + ";");
		methods.add(0, cmethod);

		//添加默认构造方法
		Method dcmethod = new Method(topLevelClass.getType().getShortName());
		dcmethod.setConstructor(true);
		dcmethod.setVisibility(JavaVisibility.PUBLIC);
		dcmethod.addBodyLine("");
		methods.add(0, dcmethod);

		//introspectedTable.getAllColumns().get(0).getRemarks();

		Map<String, String> map = DBUtils.getColumnComment(introspectedTable.getTableConfiguration().getSchema(),
				tablename(introspectedTable));
		for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
			String c = map.get(column.getActualColumnName());
			if (c != null) {
				map.put(column.getJavaProperty(), c);
			}
		}
		System.out.println(map);
		for (org.mybatis.generator.api.dom.java.Field field : topLevelClass.getFields()) {
			String c = map.get(field.getName());
			if (c != null && c.trim().length() > 0) {
				field.addJavaDocLine("/** " + c + "**/");
			}
		}

		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	private String tablename(IntrospectedTable table) {
		return table.getFullyQualifiedTableNameAtRuntime();
	}
}

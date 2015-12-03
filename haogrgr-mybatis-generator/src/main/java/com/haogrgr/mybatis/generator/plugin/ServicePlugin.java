package com.haogrgr.mybatis.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import com.google.common.collect.Lists;
import com.haogrgr.mybatis.generator.utils.PluginUtils;

/**
 * Service代码生成
 * 
 * @author desheng.tu
 * @date 2015年12月2日 下午8:20:50 
 *
 */
public class ServicePlugin extends PluginAdapter implements Plugin {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	private boolean generateIface() {
		return "true".equals(this.getProperties().getProperty("generateIface"));
	}

	private String getServicePackage() {
		return this.getProperties().getProperty("servicePackage");
	}

	private String getBaseServiceName() {
		return this.getProperties().getProperty("baseServiceName");
	}

	private String getServiceImplPackage() {
		return this.getProperties().getProperty("serviceImplPackage");
	}

	private String getBaseServiceSupportName() {
		return this.getProperties().getProperty("baseServiceSupportName");
	}

	private String getServiceImplNameSuffix() {
		return this.getProperties().getProperty("serviceImplNameSuffix", "");
	}

	private String getToBeReplace() {
		return this.getProperties().getProperty("toBeReplace", "");
	}

	private String getBaseMapperName() {
		return this.getProperties().getProperty("baseMapperName");
	}

	/**
	 * 生成Service, Service继承BaseServiceSupport
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> files = Lists.newArrayList();
		if (generateIface()) {
			files.add(generateServiceIface(introspectedTable));
		}

		files.add(generateServiceImpl(introspectedTable));

		return files;
	}

	private GeneratedJavaFile generateServiceImpl(IntrospectedTable table) {
		String baseName = PluginUtils.getModelTypeShortName(table).replace(getToBeReplace(), "");
		String modelName = PluginUtils.getModelTypeShortName(table);
		String pkName = PluginUtils.getPKTypeShortName(table);

		TopLevelClass topclass = new TopLevelClass(getServiceImplTypeName(baseName));
		topclass.setVisibility(JavaVisibility.PUBLIC);

		topclass.addImportedType("org.springframework.stereotype.Service");
		topclass.addAnnotation("@Service");

		topclass.addImportedType(new FullyQualifiedJavaType(PluginUtils.getModelType(table)));
		topclass.addImportedType(new FullyQualifiedJavaType(PluginUtils.getPKType(table)));
		topclass.addImportedType(new FullyQualifiedJavaType(getBaseServiceSupportName()));

		String superClassName = String.format("%s<%s, %s>", getBaseServiceSupportName(), modelName, pkName);
		topclass.setSuperClass(superClassName);

		if (generateIface()) {
			topclass.addImportedType(new FullyQualifiedJavaType(getServiceIfaceTypeName(baseName)));
			topclass.addSuperInterface(new FullyQualifiedJavaType(getServiceIfaceTypeName(baseName)));
		}

		topclass.addImportedType("javax.annotation.Resource");
		topclass.addImportedType(getServiceImplMapperFieldType(baseName, table));
		Field field = new Field();
		field.setName(getServiceImplMapperFieldName(baseName));
		field.setType(new FullyQualifiedJavaType(getServiceImplMapperFieldType(baseName, table)));
		field.setVisibility(JavaVisibility.PRIVATE);
		field.addAnnotation("@Resource");
		topclass.addField(field);

		Method method = new Method("getMapper");
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		String mrtype = String.format("%s<%s, %s>", getBaseMapperName(), modelName, pkName);
		method.setReturnType(new FullyQualifiedJavaType(mrtype));
		method.addBodyLine("return " + field.getName() + ";");
		topclass.addImportedType(method.getReturnType());
		topclass.addMethod(method);

		String targetProject = table.getContext().getJavaClientGeneratorConfiguration().getTargetProject();
		String fileEncoding = table.getContext().getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING);
		JavaFormatter javaFormatter = table.getContext().getJavaFormatter();
		GeneratedJavaFile file = new GeneratedJavaFile(topclass, targetProject, fileEncoding, javaFormatter);

		return file;
	}

	private String getServiceImplMapperFieldType(String baseName, IntrospectedTable table) {
		String pkg = table.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
		String type = pkg + "." + baseName + "Mapper";
		return type;
	}

	private String getServiceImplMapperFieldName(String baseName) {
		return baseName.substring(0, 1).toLowerCase() + baseName.substring(1) + "Mapper";
	}

	private String getServiceImplTypeName(String baseName) {
		return getServiceImplPackage() + "." + baseName + "Service" + getServiceImplNameSuffix();
	}

	private GeneratedJavaFile generateServiceIface(IntrospectedTable table) {
		String baseName = PluginUtils.getModelTypeShortName(table).replace(getToBeReplace(), "");
		String modelName = PluginUtils.getModelTypeShortName(table);
		String pkName = PluginUtils.getPKTypeShortName(table);

		Interface topclass = new Interface(getServiceIfaceTypeName(baseName));
		topclass.setVisibility(JavaVisibility.PUBLIC);

		topclass.addImportedType(new FullyQualifiedJavaType(PluginUtils.getModelType(table)));
		topclass.addImportedType(new FullyQualifiedJavaType(PluginUtils.getPKType(table)));
		topclass.addImportedType(new FullyQualifiedJavaType(getBaseServiceName()));

		String superIfaceName = String.format("%s<%s, %s>", getBaseServiceName(), modelName, pkName);
		topclass.addSuperInterface(new FullyQualifiedJavaType(superIfaceName));

		String targetProject = table.getContext().getJavaClientGeneratorConfiguration().getTargetProject();
		String fileEncoding = table.getContext().getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING);
		JavaFormatter javaFormatter = table.getContext().getJavaFormatter();
		GeneratedJavaFile file = new GeneratedJavaFile(topclass, targetProject, fileEncoding, javaFormatter);

		return file;
	}

	private String getServiceIfaceTypeName(String baseName) {
		return getServicePackage() + "." + baseName + "Service";
	}

}

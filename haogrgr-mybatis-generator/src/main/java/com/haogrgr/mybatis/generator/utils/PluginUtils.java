package com.haogrgr.mybatis.generator.utils;

import org.mybatis.generator.api.IntrospectedTable;

public class PluginUtils {

	/**
	 * 获取Model类名
	 */
	public static String getModelName(IntrospectedTable introspectedTable) {
		return introspectedTable.getTableConfiguration().getDomainObjectName();
	}

	/**
	 * 获取主键的类型名
	 */
	public static String getPKTypeShortName(IntrospectedTable introspectedTable) {
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			return introspectedTable.getPrimaryKeyType().substring(
					introspectedTable.getPrimaryKeyType().lastIndexOf('.') + 1);
		}
		else {
			return introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
		}
	}

	/**
	 * 替换target指定field中的包含的内容
	 */
	public static void replace(Object target, String field, String toBeReplace, String replaceMent) {
		Reflects.setField(target, field,
				Reflects.getField(target, field, String.class).replace(toBeReplace, replaceMent));
	}

}

package com.haogrgr.mybatis.generator.utils;

import org.mybatis.generator.api.IntrospectedTable;

public class PluginUtils {

	/**
	 * 获取Model类名
	 */
	public static String getModelTypeShortName(IntrospectedTable table) {
		return table.getTableConfiguration().getDomainObjectName();
	}

	public static String getModelType(IntrospectedTable table) {
		return table.getBaseRecordType();
	}

	/**
	 * 获取主键的类型名
	 */
	public static String getPKTypeShortName(IntrospectedTable table) {
		if (table.getRules().generatePrimaryKeyClass()) {
			return table.getPrimaryKeyType().substring(table.getPrimaryKeyType().lastIndexOf('.') + 1);
		}
		else {
			return table.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
		}
	}

	public static String getPKType(IntrospectedTable table) {
		if (table.getRules().generatePrimaryKeyClass()) {
			return table.getPrimaryKeyType();
		}
		else {
			return table.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getFullyQualifiedName();
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

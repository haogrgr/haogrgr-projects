package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public abstract class AbstractMethodBuilder {

	private IntrospectedTable table;

	public abstract XmlElement build();

	protected Attribute getEleAttr(XmlElement ele, String attrName) {
		for (Attribute attr : ele.getAttributes()) {
			if (attr.getName().equals(attrName)) {
				return attr;
			}
		}
		return null;
	}

	protected String tableName() {
		return table.getFullyQualifiedTableNameAtRuntime();
	}

	protected String pkName() {
		return MyBatis3FormattingUtilities.getEscapedColumnName(table.getPrimaryKeyColumns().get(0));
	}

	protected boolean generatePrimaryKeyClass() {
		return table.getRules().generatePrimaryKeyClass();
	}

	public IntrospectedTable getTable() {
		return table;
	}

	public AbstractMethodBuilder with(IntrospectedTable table) {
		this.table = table;
		return this;
	}
}

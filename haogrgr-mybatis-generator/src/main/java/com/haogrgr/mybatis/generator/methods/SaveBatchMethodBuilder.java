package com.haogrgr.mybatis.generator.methods;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class SaveBatchMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("insert");
		ele.addAttribute(new Attribute("id", "saveBatch"));
		ele.addAttribute(new Attribute("parameterType", "java.util.List"));

		StringBuilder insertClause = new StringBuilder("insert into ");
		insertClause.append(tableName() + " (");

		StringBuilder valuesClause = new StringBuilder("(");

		Iterator<IntrospectedColumn> iter = getTable().getAllColumns().iterator();
		while (iter.hasNext()) {
			IntrospectedColumn column = iter.next();

			//TODO:联合主键, 以及非自动生成方式情况考虑
			if (column.isIdentity()) {
				continue;
			}

			insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
			valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(column, "item."));
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

		return ele;
	}

}

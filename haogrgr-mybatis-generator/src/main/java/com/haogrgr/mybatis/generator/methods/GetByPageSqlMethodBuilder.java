package com.haogrgr.mybatis.generator.methods;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class GetByPageSqlMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("sql");
		ele.addAttribute(new Attribute("id", "getByPageSql"));

		ele.addElement(new TextElement("select a.* from " + tableName() + " a where 1=1 "));

		Iterator<IntrospectedColumn> iter = getTable().getAllColumns().iterator();
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

		ele.addElement(new TextElement("order by a." + pkName() + " desc"));

		return ele;
	}

}

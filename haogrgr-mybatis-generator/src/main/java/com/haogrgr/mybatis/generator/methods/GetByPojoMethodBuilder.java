package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class GetByPojoMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", "getByPojo"));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		ele.addAttribute(new Attribute("parameterType", getTable().getBaseRecordType()));

		ele.addElement(new TextElement("select "));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "Base_Column_List"));
		ele.addElement(include);

		ele.addElement(new TextElement("from " + tableName() + " "));

		XmlElement where = new XmlElement("where");
		for (IntrospectedColumn column : getTable().getAllColumns()) {
			XmlElement iftag = new XmlElement("if");
			iftag.addAttribute(new Attribute("test", column.getJavaProperty() + " != null"));

			String columnStr = MyBatis3FormattingUtilities.getEscapedColumnName(column);
			String paramStr = MyBatis3FormattingUtilities.getParameterClause(column);
			iftag.addElement(new TextElement("and " + columnStr + " = " + paramStr));

			where.addElement(iftag);
		}

		ele.addElement(where);

		return ele;
	}

}

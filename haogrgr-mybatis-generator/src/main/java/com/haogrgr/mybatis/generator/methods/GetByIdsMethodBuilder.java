package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class GetByIdsMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", "getByIds"));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));

		if (generatePrimaryKeyClass()) {
			System.err.println("loads : " + tableName() + " has complex primary key.");
			ele.addElement(new TextElement("unsupport method"));
			return ele;
		}

		ele.addElement(new TextElement("select "));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "Base_Column_List"));
		ele.addElement(include);

		ele.addElement(new TextElement("from " + tableName() + " where " + pkName() + " in "));

		XmlElement foreach = new XmlElement("foreach");
		foreach.addAttribute(new Attribute("collection", "list"));
		foreach.addAttribute(new Attribute("item", "item"));
		foreach.addAttribute(new Attribute("open", "("));
		foreach.addAttribute(new Attribute("separator", ","));
		foreach.addAttribute(new Attribute("close", ")"));
		foreach.addElement(new TextElement("#{item}"));
		ele.addElement(foreach);

		return ele;
	}

}

package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class GetAllCountMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", "getAllCount"));
		ele.addAttribute(new Attribute("resultType", "java.lang.Integer"));

		ele.addElement(new TextElement("select count(1) from " + tableName()));

		return ele;
	}

}

package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class GetAllMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", "getAll"));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));

		ele.addElement(new TextElement("select "));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "Base_Column_List"));
		ele.addElement(include);

		ele.addElement(new TextElement("from " + tableName()));

		return ele;
	}

}

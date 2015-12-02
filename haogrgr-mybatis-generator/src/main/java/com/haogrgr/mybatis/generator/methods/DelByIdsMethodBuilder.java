package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class DelByIdsMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("delete");
		ele.addAttribute(new Attribute("id", "delByIds"));

		//多主键, 不能批量删除, 留空
		if (generatePrimaryKeyClass()) {
			System.err.println("deletes : " + tableName() + " has complex primary key.");
			ele.addElement(new TextElement("unsupport method"));
			return ele;
		}

		ele.addElement(new TextElement("delete from " + tableName() + " where " + pkName() + " in "));

		XmlElement foreach = new XmlElement("foreach");
		foreach.addAttribute(new Attribute("collection", "pks"));
		foreach.addAttribute(new Attribute("item", "item"));
		foreach.addAttribute(new Attribute("open", "("));
		foreach.addAttribute(new Attribute("separator", ","));
		foreach.addAttribute(new Attribute("close", ")"));
		foreach.addElement(new TextElement("#{item}"));

		ele.addElement(foreach);

		return ele;
	}

}

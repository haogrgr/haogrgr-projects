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

		ele.addElement(new TextElement("select "));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "Base_Column_List"));

		ele.addElement(include);

		XmlElement foreach = foreach();

		//联合主键使用mysql特殊语法, 5.7.3之前, 使用的是全表扫描, 后面的版本会优化为范围扫描
		if (generatePrimaryKeyClass()) {
			ele.addElement(new TextElement("from " + tableName() + " where (" + pkNames() + ") in "));
			foreach.addElement(new TextElement("(" + pkValues("item.") + ")"));
		}
		else {
			ele.addElement(new TextElement("from " + tableName() + " where " + pkName() + " in "));
			foreach.addElement(new TextElement("#{item}"));
		}

		ele.addElement(foreach);

		return ele;
	}

	private XmlElement foreach() {
		XmlElement foreach = new XmlElement("foreach");
		foreach.addAttribute(new Attribute("collection", "list"));
		foreach.addAttribute(new Attribute("item", "item"));
		foreach.addAttribute(new Attribute("open", "("));
		foreach.addAttribute(new Attribute("separator", ","));
		foreach.addAttribute(new Attribute("close", ")"));
		return foreach;
	}

}

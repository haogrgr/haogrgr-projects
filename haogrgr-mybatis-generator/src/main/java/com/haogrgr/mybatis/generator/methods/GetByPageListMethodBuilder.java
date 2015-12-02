package com.haogrgr.mybatis.generator.methods;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class GetByPageListMethodBuilder extends AbstractMethodBuilder {

	@Override
	public XmlElement build() {
		XmlElement ele = new XmlElement("select");
		ele.addAttribute(new Attribute("id", "getByPageList"));
		ele.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		ele.addAttribute(new Attribute("parameterType", "PageInfo"));

		ele.addElement(new TextElement("select temp.* from ("));

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "getByPageSql"));
		ele.addElement(include);

		ele.addElement(new TextElement(") temp limit #{offset}, #{pageSize}"));

		return ele;
	}

}

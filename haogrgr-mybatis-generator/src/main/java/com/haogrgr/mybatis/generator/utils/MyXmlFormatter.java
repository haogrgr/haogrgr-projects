package com.haogrgr.mybatis.generator.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.DefaultXmlFormatter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 对xml文件进行格式化,具体的逻辑是
 * 对xml里面的sql按照个人习惯进行排序(比如我喜欢查询放前面...)
 * 对xml里面的每个sql后面添加空行,默认生成的没有空行.
 * <p>Author: desheng.tu</p>
 * <p>Date: 2014年8月5日</p>
 */
public class MyXmlFormatter extends DefaultXmlFormatter implements XmlFormatter {

	private static int inc = 1;
	private static HashMap<String, Integer> weightMap = new HashMap<String, Integer>();
	{
		weightMap.put("BaseResultMap", inc++);
		weightMap.put("Base_Column_List", inc++);
		weightMap.put("findById", inc++);
		weightMap.put("findByPageSql", inc++);
		weightMap.put("findByPage", inc++);
		weightMap.put("findByPageCount", inc++);
		weightMap.put("load", inc++);
		weightMap.put("update", inc++);
		weightMap.put("all", inc++);
		weightMap.put("count", inc++);
		weightMap.put("insert", inc++);
		weightMap.put("inserts", inc++);
		weightMap.put("delete", inc++);
		weightMap.put("deletes", inc++);
	}

	@Override
	public String getFormattedContent(Document document) {
		XmlElement mapper = document.getRootElement();

		//按自己的喜好排序
		Collections.sort(mapper.getElements(), new Comparator<Element>() {
			@Override
			public int compare(Element o1, Element o2) {
				String o1Id = getEleAttr(o1, "id").getValue();
				String o2Id = getEleAttr(o2, "id").getValue();
				return weightMap.get(o1Id).intValue() - weightMap.get(o2Id).intValue();
			}
		});

		//每个sql元素后插入空行
		int size = mapper.getElements().size();
		for (int i = 0, offset = 1, index = 1; i < size - 1; i++, offset++, index = i + offset) {
			mapper.addElement(index, new TextElement(""));
		}

		String xml = super.getFormattedContent(document);

		return xml;
	}

	private Attribute getEleAttr(Element ele, String attrName) {
		XmlElement xmlele = (XmlElement) ele;
		for (Attribute attr : xmlele.getAttributes()) {
			if (attr.getName().equals(attrName)) {
				return attr;
			}
		}
		return null;
	}
}

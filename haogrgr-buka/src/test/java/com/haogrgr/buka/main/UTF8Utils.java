package com.haogrgr.buka.main;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class UTF8Utils {

	public static void main(String[] args) throws Exception {
		InputStream input = UTF8Utils.class.getResourceAsStream("/json.json");
		String text = IOUtils.toString(input);
		System.out.println(text);
		
		String regex = "\"intro\":.*(?=,\"lastup\")";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(text);
		if(matcher.find()){
			String group = matcher.group();
			group = group.substring(regex.indexOf("*"));
			System.err.println(group);
		}
	}
	            	
}

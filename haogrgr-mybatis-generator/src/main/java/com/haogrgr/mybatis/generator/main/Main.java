package com.haogrgr.mybatis.generator.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Main {

	public static List<String> warnings = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		System.setProperty("mybatis.generator.overwrite", "true");

		File configFile = new File(Main.class.getResource("/genrator.xml").toURI());

		Configuration config = new ConfigurationParser(warnings).parseConfiguration(configFile);
		MyBatisGenerator generator = new MyBatisGenerator(config, new DefaultShellCallback(true), warnings);

		generator.generate(null);

		System.out.println(warnings);
	}

}

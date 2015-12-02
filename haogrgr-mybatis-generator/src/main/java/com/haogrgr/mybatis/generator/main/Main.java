package com.haogrgr.mybatis.generator.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Main {

	public static void main(String[] args) throws Exception {
		System.setProperty("mybatis.generator.overwrite", "true");

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(Main.class.getResource("/genrator.xml").toURI());
		init();

		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
		generator.generate(null);

		System.out.println(warnings);
	}

	public static void init() {
		File file = new File("C:\\mybatis");
		if (file.exists()) {
			System.out.println(file.delete());
		}
		file.mkdirs();
	}

}

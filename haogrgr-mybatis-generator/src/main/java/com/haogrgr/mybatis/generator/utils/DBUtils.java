package com.haogrgr.mybatis.generator.utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import groovy.sql.GroovyRowResult;
import groovy.sql.Sql;

public class DBUtils {

	private static Sql sql = null;
	static {
		try {
			sql = Sql
					.newInstance(
							"jdbc:mysql://localhost:3306/information_schema?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
							"root", "123456", "com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getColumnComment("growth-channel-download", "channel_download_visit_log");
	}

	public static Map<String, String> getColumnComment(String schema, String table) {
		try {
			HashMap<String, String> map = new HashMap<>();
			List<GroovyRowResult> rows = sql
					.rows("select COLUMN_NAME, COLUMN_COMMENT from columns where table_schema ='" + schema
							+ "' and table_name = '" + table + "'");

			for (GroovyRowResult row : rows) {
				Object name = row.getProperty("COLUMN_NAME");
				Object comment = row.getProperty("COLUMN_COMMENT");
				map.put((String) name, (String) comment);
			}

			System.out.println(map);

			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}

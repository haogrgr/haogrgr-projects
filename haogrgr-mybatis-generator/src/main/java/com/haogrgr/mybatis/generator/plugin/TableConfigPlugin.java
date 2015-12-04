package com.haogrgr.mybatis.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.TableConfiguration;

/**
 * 配置table, 将本来需要配置的内容移到代码里面来,
 * 
 * enableSelectByExample="false" enableDeleteByExample="false" enableCountByExample="false" enableUpdateByExample="false" selectByExampleQueryId="false"
 * 
 * 减少配置时心智压力, 一长串的参数, 看得心里压力大.
 * 
 * @author desheng.tu
 * @date 2015年12月4日 下午3:27:32 
 *
 */
public class TableConfigPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		TableConfiguration config = introspectedTable.getTableConfiguration();
		config.setSelectByExampleStatementEnabled(false);
		config.setDeleteByExampleStatementEnabled(false);
		config.setCountByExampleStatementEnabled(false);
		config.setUpdateByExampleStatementEnabled(false);
	}
}

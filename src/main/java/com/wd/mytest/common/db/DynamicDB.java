package com.wd.mytest.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author GuoYJ @create 2016年11月1日
 * 
 *
 */
public class DynamicDB extends AbstractRoutingDataSource {
	
	private static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void setDB(String dbType) {
		holder.set(dbType);
	}
	
	public static void setDefault() {
		holder.set("sqlserverDataSource");
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return holder.get();
	}
}
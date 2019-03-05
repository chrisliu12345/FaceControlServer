package com.gd.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//创建类DynamicRoutingDataSource继承AbstractRoutingDataSource类并且实现determineCurrentLookupKey()方法，设置数据源。
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("数据源选择"+DynamicDataSourceContextHolder.get().name());
        return DynamicDataSourceContextHolder.get();
    }
}
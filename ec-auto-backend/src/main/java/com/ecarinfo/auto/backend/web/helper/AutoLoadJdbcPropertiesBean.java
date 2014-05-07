package com.ecarinfo.auto.backend.web.helper;

import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.ecarinfo.persist.simple.DaoTool;

@Component
public class AutoLoadJdbcPropertiesBean implements BeanFactoryAware{

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
/*		PoolProperties poolProperties = beanFactory.getBean(PoolProperties.class);
		DaoTool.init(poolProperties);
		System.out.println(poolProperties);*/

//		DataSource dataSource = beanFactory.getBean(org.apache.tomcat.jdbc.pool.DataSource.class);

		DataSource dataSource = beanFactory.getBean(com.alibaba.druid.pool.DruidDataSource.class);
		DaoTool.init(dataSource);
	}
}

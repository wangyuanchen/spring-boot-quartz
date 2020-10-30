package com.wyc.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:25
 * @description:
 */
@Configuration
@MapperScan(value = "com.wyc.demo.dao.demo", sqlSessionTemplateRef = "demoSqlSessionTemplate")
public class DemoMybatisConfig {

    @Bean(name = "demoTransactionManager")
    public DataSourceTransactionManager adminTransactionManager(@Qualifier("demoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "demoSqlSessionFactory")
    public SqlSessionFactory adminSqlSessionFactory(@Qualifier("demoDataSource") DataSource dataSource, @Value("${mybatis.demo.mapper-locations}") Resource[] mappers) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(mappers);
        return factoryBean.getObject();
    }

    @Bean(name = "demoSqlSessionTemplate")
    public SqlSessionTemplate adminSqlSessionTemplate(@Qualifier("demoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}

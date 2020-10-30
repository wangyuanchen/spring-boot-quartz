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
@MapperScan(value = "com.aisino.social.credit.quartz.dao", sqlSessionTemplateRef = "quartzSqlSessionTemplate")
public class QuartzMybatisConfig {

    @Bean(name = "quartzTransactionManager")
    public DataSourceTransactionManager adminTransactionManager(@Qualifier("quartzDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "quartzSqlSessionFactory")
    public SqlSessionFactory adminSqlSessionFactory(@Qualifier("quartzDataSource") DataSource dataSource, @Value("${mybatis.quartz.mapper-locations}") Resource[] mappers) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(mappers);
        return factoryBean.getObject();
    }

    @Bean(name = "quartzSqlSessionTemplate")
    public SqlSessionTemplate adminSqlSessionTemplate(@Qualifier("quartzSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}

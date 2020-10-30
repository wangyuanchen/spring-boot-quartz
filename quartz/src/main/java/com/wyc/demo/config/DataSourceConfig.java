package com.wyc.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:25
 * @description:
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean("quartzDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.quartz")
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("demoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.demo")
    public DataSource accountDataSource() {
        return DataSourceBuilder.create().build();
    }

}

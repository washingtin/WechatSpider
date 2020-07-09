package com.seven.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 数据库配置
 *
 * @author 最爱吃小鱼
 */
@Configuration
public class DataBaseConfig {

    // 数据分页插件
    @Bean(name = "paginationInterceptor")
    public PaginationInterceptor getPaginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        paginationInterceptor.setOverflowCurrent(true);
        return paginationInterceptor;
    }


    // cloud数据库配置
    @Bean(name = "cloudDataSource")
    @ConfigurationProperties(prefix = "cloud.datasource")
    @Primary
    public DataSource cloudDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "cloudSqlSessionFactory")
    public MybatisSqlSessionFactoryBean cloudMybatisSqlSessionFactoryBean(@Qualifier("cloudDataSource")DataSource dataSource, Interceptor interceptor) throws IOException {
        return initMybatisSqlSessionFactoryBean(dataSource, interceptor, "cloud");
    }

    @Bean(name = "cloudMapperScannerConfigurer")
    public MapperScannerConfigurer cloudMapperScannerConfigurer() {
        return initMapperScannerConfigurer("cloudSqlSessionFactory", "cloud");
    }


    // SqlSessionFactoryBean的构建
    private MybatisSqlSessionFactoryBean initMybatisSqlSessionFactoryBean(DataSource dataSource, Interceptor interceptor, String dbKey) throws IOException {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setVfs(SpringBootVFS.class);
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        bean.setTypeAliasesPackage("com.seven.wechat.bean.**");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(String.format("classpath:%s/*/*Mapper.xml", dbKey)));
        bean.setPlugins(new Interceptor[]{interceptor});
        return bean;
    }

    // MapperScannerConfigurer的构建
    private MapperScannerConfigurer initMapperScannerConfigurer(String sqlSessionFactoryBeanName, String dbKey) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(String.format("com.seven.wechat.%s", dbKey));
        configurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        return configurer;
    }
}

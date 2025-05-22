package com.anypluspay.admin.basis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * basis数据源配置
 * @author wxj
 * 2025/3/21
 */
@Configuration
@MapperScan(basePackages = {"com.anypluspay.admin.basis.mapper"}, sqlSessionFactoryRef = "adminSqlSessionFactory")
public class AdminDataSourceConfigure {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    @Bean(name = "adminDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.admin")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "adminSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("adminDataSource") DataSource dataSource, MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.addInterceptor(mybatisPlusInterceptor);

        factoryBean.setConfiguration(mybatisConfiguration);
        factoryBean.setMapperLocations(resourceResolver.getResources("classpath*:/mapper/admin/*.xml"));

        return factoryBean.getObject();
    }

    @Bean(name = "adminTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("adminDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "adminTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("adminTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}

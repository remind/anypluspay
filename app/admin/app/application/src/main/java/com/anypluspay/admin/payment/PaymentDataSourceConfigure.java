package com.anypluspay.admin.payment;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
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
 * @author wxj
 * 2025/3/28
 */
@Configuration
@MapperScan(basePackages = {"com.anypluspay.payment.infra.persistence.mapper"}, sqlSessionFactoryRef = "paymentSqlSessionFactory", nameGenerator = PaymentBeanNameGenerator.class)
public class PaymentDataSourceConfigure {
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    @Bean(name = "paymentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.payment")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "paymentSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("paymentDataSource") DataSource dataSource, MybatisPlusInterceptor mybatisPlusInterceptor,
                                               @Qualifier("autoFillMetaObjectHandler") MetaObjectHandler autoFillMetaObjectHandler) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(autoFillMetaObjectHandler); // 手动注入 MetaObjectHandler
        factoryBean.setGlobalConfig(globalConfig);

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.addInterceptor(mybatisPlusInterceptor);

        factoryBean.setConfiguration(mybatisConfiguration);
        factoryBean.setMapperLocations(resourceResolver.getResources("classpath*:/mapper/payment/*.xml"));

        return factoryBean.getObject();
    }

    @Bean(name = "paymentTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("paymentDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "paymentTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("paymentTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}

package com.anypluspay.testtrade.basis;

import com.alibaba.druid.pool.DruidDataSource;
import com.anypluspay.admin.basis.TestTradeBeanNameGenerator;
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
 * @author wxj
 * 2025/4/8
 */
@Configuration
@MapperScan(basePackages = {"com.anypluspay.testtrade.infra.persistence.mapper"}, sqlSessionFactoryRef = "testtradeSqlSessionFactory", nameGenerator = TestTradeBeanNameGenerator.class)
public class TestTradeDataSourceConfigure {
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    @Bean(name = "testtradeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.testtrade")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "testtradeSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("testtradeDataSource") DataSource dataSource, MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.addInterceptor(mybatisPlusInterceptor);

        factoryBean.setConfiguration(mybatisConfiguration);
//        factoryBean.setMapperLocations(resourceResolver.getResources("classpath*:/mapper/testtrade/*.xml"));
        factoryBean.setMapperLocations(resourceResolver.getResources("classpath*:/com/anypluspay/testtrade/infra/persistence/mapper/xml/*.xml"));

        return factoryBean.getObject();
    }

    @Bean(name = "testtradeTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("testtradeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "testtradeTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("testtradeTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}

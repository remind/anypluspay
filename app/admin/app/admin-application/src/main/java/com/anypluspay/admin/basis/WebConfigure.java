package com.anypluspay.admin.basis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author wxj
 * 2025/3/20
 */
@Configuration
public class WebConfigure {

    @Bean("jdbcTemplateMap")
    public Map<String, JdbcTemplate> sqlSessionFactory(@Qualifier("paymentDataSource") DataSource paymentDataSource,
                                                       @Qualifier("accountDataSource") DataSource accountDataSource,
                                                       @Qualifier("channelDataSource") DataSource channelDataSource) {
        return Map.of(
                "payment", new JdbcTemplate(paymentDataSource),
                "account", new JdbcTemplate(accountDataSource),
                "channel", new JdbcTemplate(channelDataSource));
    }
}

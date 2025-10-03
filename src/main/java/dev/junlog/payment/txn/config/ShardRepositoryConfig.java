package dev.junlog.payment.txn.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ShardRepositoryConfig {

    @Bean(name = "globalJdbcTemplate")
    public JdbcTemplate globalJdbcTemplate(@Qualifier("globalDataSource") DataSource globalDataSource) {
        return new JdbcTemplate(globalDataSource);
    }

    @Bean(name = "shardRoutingJdbcTemplate")
    public JdbcTemplate shardRoutingJdbcTemplate(@Qualifier("shardRoutingDataSource") DataSource shardRoutingDataSource) {
        return new JdbcTemplate(shardRoutingDataSource);
    }
}

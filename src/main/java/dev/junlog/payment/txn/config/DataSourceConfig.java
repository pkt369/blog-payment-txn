package dev.junlog.payment.txn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "globalDataSource")
    @ConfigurationProperties("spring.datasource.global")
    public DataSource globalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "shardRoutingDataSource")
    public DataSource shardRoutingDataSource(Environment env) {
        Map<Object, Object> targetDataSources = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            String prefix = "spring.datasource.shards.db" + i;
            DataSource ds = DataSourceBuilder.create()
                .url(env.getProperty(prefix + ".url"))
                .username(env.getProperty(prefix + ".username"))
                .password(env.getProperty(prefix + ".password"))
                .driverClassName("org.postgresql.Driver")
                .build();
            targetDataSources.put("db" + i, ds);
        }

        ShardRoutingDataSource routingDataSource = new ShardRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }
}
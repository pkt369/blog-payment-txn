package dev.junlog.payment.txn.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setShardKey(Long userId, int shardCount) {
        int shardId = (int)(userId % shardCount);
        contextHolder.set("db" + shardId);
    }

    public static void clear() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }
}

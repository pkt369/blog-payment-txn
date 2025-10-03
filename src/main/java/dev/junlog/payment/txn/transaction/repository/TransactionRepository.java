package dev.junlog.payment.txn.transaction.repository;

import dev.junlog.payment.txn.transaction.dao.Transaction;
import dev.junlog.payment.txn.transaction.dao.TransactionStatus;
import dev.junlog.payment.txn.transaction.dao.TransactionType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(@Qualifier("shardRoutingJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transactions(user_id, amount, status, type, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(sql,
            new Object[]{
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getStatus().name(),
                transaction.getType().name(),
                Timestamp.valueOf(transaction.getCreatedAt()),
                Timestamp.valueOf(transaction.getUpdatedAt())
            },
            Long.class);
        transaction.setId(id);
        return transaction;
    }

    public Optional<Transaction> findById(Long id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        List<Transaction> list = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            return Transaction.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .amount(rs.getBigDecimal("amount"))
                .status(TransactionStatus.valueOf(rs.getString("status")))
                .type(TransactionType.valueOf(rs.getString("type")))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
        });
        return list.stream().findFirst();
    }

    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return Transaction.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .amount(rs.getBigDecimal("amount"))
                .status(TransactionStatus.valueOf(rs.getString("status")))
                .type(TransactionType.valueOf(rs.getString("type")))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
        });
    }

    public Transaction updateStatus(Long id, TransactionStatus status) {
        String sql = "UPDATE transactions SET status = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, status.name(), Timestamp.valueOf(java.time.LocalDateTime.now()), id);

        return findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}

package dev.junlog.payment.txn.user.repository;

import dev.junlog.payment.txn.user.dao.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(@Qualifier("globalJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        jdbcTemplate.update(
            "INSERT INTO users(name, created_at) VALUES (?, ?)",
            user.getName(),
            user.getCreatedAt()
        );
    }

    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{id},
            (ResultSet rs) -> {
                if (rs.next()) {
                    return Optional.of(
                        User.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                            .build()
                    );
                }
                return Optional.empty();
            }
        );
    }

}

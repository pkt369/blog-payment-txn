package dev.junlog.payment.txn.user.dao;

import dev.junlog.payment.txn.user.dto.UserRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static User fromDto(UserRequest userRequest) {
        return User.builder()
            .name(userRequest.name())
            .createdAt(LocalDateTime.now())
            .build();
    }
}

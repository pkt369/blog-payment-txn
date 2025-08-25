package dev.junlog.payment.txn.user.service;

import dev.junlog.payment.txn.user.dao.User;
import dev.junlog.payment.txn.user.dto.UserRequest;
import dev.junlog.payment.txn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void save(UserRequest userRequest) {
        User user = User.fromDto(userRequest);
        userRepository.save(user);
    }
}

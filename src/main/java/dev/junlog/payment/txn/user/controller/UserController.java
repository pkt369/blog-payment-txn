package dev.junlog.payment.txn.user.controller;

import dev.junlog.payment.txn.user.dto.UserRequest;
import dev.junlog.payment.txn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> create(UserRequest userRequest) {
        userService.save(userRequest);
        return ResponseEntity.ok("ok");
    }
}

package com.queuecompanion.mup.controllers;

import com.queuecompanion.mup.dto.request.LoginRequest;
import com.queuecompanion.mup.dto.request.RegisterRequest;
import com.queuecompanion.mup.services.AuthService;
import com.queuecompanion.mup.util.HttpConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(HttpConstants.AUTH_PATH)
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // TODO: https://www.codejava.net/frameworks/spring-boot/connect-to-postgresql-database-examples
    @PostMapping(HttpConstants.REGISTER_PATH)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(HttpConstants.LOGIN_PATH)
    public ResponseEntity<Void> login(LoginRequest request) {
        // TODO
        return ResponseEntity.ok().build();
    }

    // TODO: authorize
    // TODO: username may not be needed, since from the HTTP context, you should know which user is trying to logout
    @PostMapping(HttpConstants.LOGOUT_PATH)
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    // TODO email confirmation

    // TODO password reset
}

package com.plusone.mup.controllers;

import com.plusone.mup.dto.request.LoginRequest;
import com.plusone.mup.dto.request.RegisterRequest;
import com.plusone.mup.dto.response.LoginResponse;
import com.plusone.mup.dto.response.RegisterResponse;
import com.plusone.mup.services.AuthService;
import com.plusone.mup.util.HttpConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(HttpConstants.REGISTER_PATH)
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        // TODO
        return ResponseEntity.ok(new RegisterResponse());
    }

    @PostMapping(HttpConstants.LOGIN_PATH)
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        // TODO
        return ResponseEntity.ok(new LoginResponse());
    }

    // TODO: authorize
    // TODO: username may not be needed, since from the HTTP context, you should know which user is trying to logout
    @PostMapping(HttpConstants.LOGOUT_PATH)
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}

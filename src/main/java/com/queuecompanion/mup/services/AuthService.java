package com.queuecompanion.mup.services;

import com.queuecompanion.mup.dto.request.LoginRequest;
import com.queuecompanion.mup.dto.request.RegisterRequest;
import com.queuecompanion.mup.exceptions.MupException;
import com.queuecompanion.mup.persistence.models.User;
import com.queuecompanion.mup.persistence.repositories.IUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final IUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(IUserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    // TODO: check-session lib in SBG

    public void register(RegisterRequest request) {
        if (userDao.findByUsername(request.getUsername()) != null) {
            throw new MupException("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if (userDao.findByEmailAddress(request.getEmailAddress()) != null) {
            throw new MupException("Email address is already taken", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .withUsername(request.getUsername())
                .withEmailAddress(request.getEmailAddress())
                .withPassword(encodedPassword)
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withIsoCountryCode(request.getIsoCountryCode())
                .build();

        userDao.save(user);
    }

    public String login(LoginRequest request) {
        User user = userDao.findByUsernameOrEmailAddress(request.getIdentifier(), request.getIdentifier());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new MupException("Invalid authentication information", HttpStatus.UNAUTHORIZED);
        }

        // TODO: actual implementation that communicates with separate micro-service to get sessionId
        return UUID.randomUUID().toString();
    }
}

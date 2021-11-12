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
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final IUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(IUserDao userDao, Pbkdf2PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    // TODO: check-session lib in SBG

    public void register(RegisterRequest registerRequest) {
        if (userDao.findByUsername(registerRequest.getUsername()) != null) {
            throw new MupException("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if (userDao.findByEmail(registerRequest.getEmailAddress()) != null) {
            throw new MupException("Email address is already taken", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder()
                .withUsername(registerRequest.getUsername())
                .withEmailAddress(registerRequest.getEmailAddress())
                .withPassword(encodedPassword)
                .withFirstName(registerRequest.getFirstName())
                .withLastName(registerRequest.getLastName())
                .withIsoCountryCode(registerRequest.getIsoCountryCode())
                .build();

        userDao.save(user);
    }

//    // TODO: replace String with DTO if needed
//    public String openSession(LoginRequest loginRequest) {
//        // 1. check if username exists and if password is valid
//        // 2. generate session in a separate service
//    }
}

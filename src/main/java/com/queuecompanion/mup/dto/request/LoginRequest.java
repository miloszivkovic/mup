package com.queuecompanion.mup.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

// TODO Record ? Apply to other classes if possible https://www.baeldung.com/java-record-keyword
public class LoginRequest {
    @NotBlank(message = "Username / email is required")
    private final String identifier;

    @NotBlank(message = "Password is required")
    private final String password;

    public LoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}

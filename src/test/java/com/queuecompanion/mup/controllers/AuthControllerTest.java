package com.queuecompanion.mup.controllers;

import com.queuecompanion.mup.dto.request.LoginRequest;
import com.queuecompanion.mup.dto.request.RegisterRequest;
import com.queuecompanion.mup.util.HttpConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: RANDOM_PORT to avoid conflicts
// TODO: can you use same web server for all test classes?
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=8042",
                "management.server.port=9042"
        }
)
public class AuthControllerTest {
    private static final String AUTH_URL = "http://localhost:8042" + HttpConstants.AUTH_PATH;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testRegisterHappyPath() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, registerRequest, Void.class);
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());

        // TODO: mock verification email sending
    }

    @Test
    void testRegisterUsernameAlreadyTaken() {

    }

    @Test
    void testRegisterEmailAlreadyTaken() {

    }

    @Test
    void testRegisterInvalidUsernameNull() {
        RegisterRequest usernameNull = RegisterRequest.builder()
                .withUsername(null)
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, usernameNull, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidUsernameTooShort() {
        RegisterRequest usernameTooShort = RegisterRequest.builder()
                .withUsername("u")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, usernameTooShort, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidUsernameTooLong() {
        RegisterRequest usernameTooLong = RegisterRequest.builder()
                .withUsername("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, usernameTooLong, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidUsernameContainsInvalidCharacter() {
        RegisterRequest usernameContainsInvalidCharacter = RegisterRequest.builder()
                .withUsername("username~")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, usernameContainsInvalidCharacter, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPassword() {
// TODO: test when passwords don't match
    }

    @Test
    void testRegisterInvalidPasswordNull() {

    }

    @Test
    void testRegisterInvalidPasswordTooShort() {

    }

    @Test
    void testRegisterInvalidPasswordTooLong() {

    }

    @Test
    void testRegisterInvalidPasswordNoUppercase() {

    }

    @Test
    void testRegisterInvalidPasswordNoLowerCase() {

    }

    @Test
    void testRegisterInvalidPasswordNoDigit() {

    }

    @Test
    void testRegisterInvalidPasswordNoSpecialCharacter() {

    }

    @Test
    void testRegisterInvalidPasswordContainsWhitespace() {

    }

    @Test
    void testRegisterInvalidPasswordsDontMatch() {

    }

    @Test
    void testRegisterInvalidFirstName() {

    }

    @Test
    void testRegisterInvalidLastName() {

    }

    @Test
    void testRegisterInvalidCountry() {

    }

    @Test
    void testRegisterPasswordDoesntMatch() {

    }

    @Test
    void testRegisterAlreadyLoggedIn() {
        // this should be fine right? we just register new user and ignore the session id header
    }

    @Test
    void testLoginWithUsernameHappyPath() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
    }

    @Test
    void testLoginWithEmailHappyPath() {
        LoginRequest loginRequest = new LoginRequest("test@queuecompanion.com", "password");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
    }

    @Test
    void testLoginUsernameNotFound() {

    }

    @Test
    void testLoginEmailAddressNotFound() {

    }

    @Test
    void testLoginInvalidPassword() {

    }

    @Test
    void testLoginAlreadyLoggedIn() {
        // this should be fine right? we just log him in again, ignoring the sessionId header, and return new session
    }

    @Test
    void testChangePasswordHappyPath() {
        // TODO: mock email sending
    }

    @Test
    void testChangePasswordUnauthorized() {

    }

    @Test
    void testLogoutHappyPath() {
        ResponseEntity<Void> logoutResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGOUT_PATH, null, Void.class);
        assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());
    }

    @Test
    void testLogoutUnauthorized() {
        // sessionId header missing or expired/invalid
    }

    // TODO: non-happy paths
}

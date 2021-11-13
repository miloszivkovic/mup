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
    void testRegisterInvalidPasswordNull() {
        RegisterRequest passwordNull = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword(null)
                .withMatchingPassword(null)
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordNull, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordTooShort() {
        RegisterRequest passwordTooShort = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Pass1!")
                .withMatchingPassword("pass")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordTooShort, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordTooLong() {
        RegisterRequest passwordTooLong = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Pass1!Pass1!Pass1!Pass1!Pass1!Pass1!Pass1!Pass1!")
                .withMatchingPassword("toolongtoolongtoolongtoolongtoolongtoolong")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordTooLong, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordNoUppercase() {
        RegisterRequest passwordNoUppercase = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("nouppercase1.")
                .withMatchingPassword("withoutuppercase")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordNoUppercase, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordNoLowerCase() {
        RegisterRequest passwordNoLowercase = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("NOLOWERCASE1.")
                .withMatchingPassword("withoutuppercase")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordNoLowercase, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordNoDigit() {
        RegisterRequest passwordNoDigit = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("PasswordNoDigit.")
                .withMatchingPassword("withoutuppercase")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordNoDigit, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordNoSpecialCharacter() {
        RegisterRequest passwordNoSpecialCharacter = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("PasswordNoChar1")
                .withMatchingPassword("withoutuppercase")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordNoSpecialCharacter, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordContainsWhitespace() {
        RegisterRequest passwordWhiteSpace = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Password 1.")
                .withMatchingPassword("withoutuppercase")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordWhiteSpace, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidPasswordsDontMatch() {
        RegisterRequest passwordsDontMatch = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Password1.")
                .withMatchingPassword("Password1!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, passwordsDontMatch, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidFirstNameNull() {
        RegisterRequest firstNameNull = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName(null)
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstNameNull, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidFirstNameTooLong() {
        RegisterRequest firstNameTooLong = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("FirstNameTooLongFirstNameTooLongFirstNameTooLong")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstNameTooLong, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidLastNameNull() {
        RegisterRequest lastNameNull = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName(null)
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, lastNameNull, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidLastNameTooLong() {
        RegisterRequest lastNameTooLong = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("LastNameTooLongLastNameTooLongLastNameTooLong")
                .withIsoCountryCode("test")
                .build();

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, lastNameTooLong, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, registerResponse.getStatusCode());
    }

    @Test
    void testRegisterInvalidCountry() {
        // TODO once I've figured out things regarding country codes etc
    }

    @Test
    void testRegisterAlreadyLoggedIn() {
        // this should be fine right? we just register new user and ignore the session id header
    }

    @Test
    void testLoginWithUsernameHappyPath() {
        LoginRequest loginRequest = new LoginRequest("username", "Qztxcrvh1.!");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
    }

    @Test
    void testLoginWithEmailHappyPath() {
        LoginRequest loginRequest = new LoginRequest("test@queuecompanion.com", "Qztxcrvh1.!");
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
        // sessionId header missing/expired/invalid
    }
}

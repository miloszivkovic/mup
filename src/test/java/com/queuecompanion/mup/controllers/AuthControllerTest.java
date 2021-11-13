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
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

// TODO: RANDOM_PORT to avoid conflicts
// TODO: can you use same web server for all test classes?
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=8042",
                "management.server.port=9042"
        }
)
// TODO: instead of this, maybe implement generateRandomUsername(), generateRandomEmail() and use those - tests would be faster

// TODO: refactor a bit, with bunch of repeated Register / Login requests, you'll mess up something for sure
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        // register successfully
        RegisterRequest firstRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> firstResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstRequest, Void.class);
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        // try again with the same username
        RegisterRequest secondRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> secondResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, secondRequest, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, secondResponse.getStatusCode());
    }

    @Test
    void testRegisterEmailAlreadyTaken() {
// register successfully
        RegisterRequest firstRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> firstResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstRequest, Void.class);
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        // try again with the same email
        RegisterRequest secondRequest = RegisterRequest.builder()
                .withUsername("newusername")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> secondResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, secondRequest, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, secondResponse.getStatusCode());
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

    // TODO: check if session is returned in response
    @Test
    void testLoginWithUsernameHappyPath() {
        // register
        RegisterRequest firstRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> firstResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstRequest, Void.class);
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        // login
        LoginRequest loginRequest = new LoginRequest("username", "Qztxcrvh1.!");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        String cookie = new String(loginResponse.getHeaders().get("Set-Cookie").get(0).getBytes());
        assertTrue(cookie.contains("session-id"));
        assertTrue(cookie.contains("Secure"));
        assertTrue(cookie.contains("HttpOnly"));
    }

    @Test
    void testLoginWithEmailHappyPath() {
        // register
        RegisterRequest firstRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> firstResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstRequest, Void.class);
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        // login
        LoginRequest loginRequest = new LoginRequest("test@queuecompanion.com", "Qztxcrvh1.!");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        String cookie = new String(loginResponse.getHeaders().get("Set-Cookie").get(0).getBytes());
        assertTrue(cookie.contains("session-id"));
        assertTrue(cookie.contains("Secure"));
        assertTrue(cookie.contains("HttpOnly"));
    }

    @Test
    void testLoginUsernameNotFound() {
        LoginRequest loginRequest = new LoginRequest("username", "Qztxcrvh1.!");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
    }

    @Test
    void testLoginEmailAddressNotFound() {
        LoginRequest loginRequest = new LoginRequest("test@queuecompanion.com", "Qztxcrvh1.!");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
    }

    @Test
    void testLoginWrongPassword() {
        // register
        RegisterRequest firstRequest = RegisterRequest.builder()
                .withUsername("username")
                .withEmailAddress("test@queuecompanion.com")
                .withPassword("Qztxcrvh1.!")
                .withMatchingPassword("Qztxcrvh1.!")
                .withFirstName("First")
                .withLastName("Last")
                .withIsoCountryCode("test")
                .build();
        ResponseEntity<Void> firstResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.REGISTER_PATH, firstRequest, Void.class);
        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());

        // login
        LoginRequest loginRequest = new LoginRequest("test@queuecompanion.com", "wrongpassword");
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(AUTH_URL + HttpConstants.LOGIN_PATH, loginRequest, Void.class);
        assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
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

package com.queuecompanion.mup.dto.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedList;
import java.util.List;

public class UsernameConstraintValidator implements ConstraintValidator<ValidUsername, String> {
    // 1. 3 - 30 characters long
    // 2. Allowed characters are a-z, A-z, 0-9, . and _
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._]{3,30}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> errors = new LinkedList<>();

        if (StringUtils.isBlank(s)) {
            errors.add("Username is required");
        } else {
            if (s.length() < 3 || s.length() > 30) {
                errors.add("Username must be between 3 and 30 characters long");
            }

            if (!s.matches(USERNAME_PATTERN)) {
                // invalid character found
                errors.add("Allowed characters for username are: a-z, A-Z, 0-9, . and _");
            }
        }

        if (errors.isEmpty()) {
            // valid
            return true;
        }

        String messageTemplate = String.join(",", errors);
        constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}

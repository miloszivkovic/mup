package com.queuecompanion.mup.dto.validators;

import com.queuecompanion.mup.dto.request.RegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MatchingPasswordConstraintValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(registerRequest.getPassword(), registerRequest.getMatchingPassword());
    }
}

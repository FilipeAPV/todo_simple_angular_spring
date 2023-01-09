package com.mytodo.backend.validations;

import com.mytodo.backend.annotations.UniqueEmail;
import com.mytodo.backend.user.UserModel;
import com.mytodo.backend.user.UserRepository;
import com.mytodo.backend.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Email already in DB")
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}

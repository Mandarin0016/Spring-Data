package com.example.football.util.impl;

import com.example.football.models.entity.Town;
import com.example.football.util.ValidationUtil;


import javax.validation.Validator;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}

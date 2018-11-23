package com.spring.valid.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ListPatternValidator implements ConstraintValidator<ListValid, List<String>> {


    @Override
    public void initialize(ListValid listValid) {




    }

    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}

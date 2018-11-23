package com.spring.valid.config;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ListPatternValidator.class)
@Documented
public @interface ListValid {

    String msg() default "List中对象不符合数据规定";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String  regexp();



}

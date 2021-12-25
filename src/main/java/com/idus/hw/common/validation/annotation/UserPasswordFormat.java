package com.idus.hw.common.validation.annotation;

import com.idus.hw.common.validation.validator.UserPasswordFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UserPasswordFormatValidator.class})
public @interface UserPasswordFormat {
    String message() default "UserPassword validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}

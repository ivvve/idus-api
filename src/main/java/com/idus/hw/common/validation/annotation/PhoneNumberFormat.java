package com.idus.hw.common.validation.annotation;

import com.idus.hw.common.validation.validator.PhoneNumberFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneNumberFormatValidator.class})
public @interface PhoneNumberFormat {
    String message() default "PhoneNumber validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}

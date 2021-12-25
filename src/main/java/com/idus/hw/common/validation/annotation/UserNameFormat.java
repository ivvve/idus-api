package com.idus.hw.common.validation.annotation;

import com.idus.hw.common.validation.validator.UserNameFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UserNameFormatValidator.class})
public @interface UserNameFormat {
    String message() default "UserName validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}

package com.idus.hw.common.validation.annotation;

import com.idus.hw.common.validation.validator.UserNicknameFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UserNicknameFormatValidator.class})
public @interface UserNicknameFormat {
    String message() default "UserNickname validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;
}

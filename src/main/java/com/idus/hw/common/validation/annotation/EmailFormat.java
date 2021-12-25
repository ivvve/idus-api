package com.idus.hw.common.validation.annotation;

import com.idus.hw.common.validation.validator.EmailFormatValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EmailFormatValidator.class})
public @interface EmailFormat {
}

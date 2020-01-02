package com.travix.medusa.busyflights.controller.validation.rule;

import com.travix.medusa.busyflights.controller.validation.rule.validator.IsValidDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/* Custom validation to check if date has the proper ISO date format */
@Documented
@Constraint(validatedBy = IsValidDateValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidDate {
    String message() default "invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

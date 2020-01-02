package com.travix.medusa.busyflights.controller.validation.rule.validator;

import com.travix.medusa.busyflights.controller.validation.rule.IsValidDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.OffsetDateTime;
import java.util.Objects;

public class IsValidDateValidator implements
        ConstraintValidator<IsValidDate, String> {

    @Override
    public void initialize(IsValidDate isValidDate) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(date)) {
            return true;
        }

        try {
            //parses the string into ISO 8601 formatted date
            OffsetDateTime.parse(date);
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }
}
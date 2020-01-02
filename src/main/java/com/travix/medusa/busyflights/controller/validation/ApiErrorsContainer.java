package com.travix.medusa.busyflights.controller.validation;

import java.util.List;
import java.util.Objects;

/* holds all field errors specific to an instance of api request */
public class ApiErrorsContainer {

    private List<ApiError> errors;

    public ApiErrorsContainer(){
    }

    public List<ApiError> getErrors(){
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }

    public boolean hasErrors(){
        return !Objects.isNull(errors) && !errors.isEmpty();
    }
}

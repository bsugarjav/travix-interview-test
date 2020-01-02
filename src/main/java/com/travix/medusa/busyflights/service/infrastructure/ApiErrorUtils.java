package com.travix.medusa.busyflights.service.infrastructure;

import com.travix.medusa.busyflights.controller.validation.ApiError;
import com.travix.medusa.busyflights.controller.validation.ApiErrorsContainer;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiErrorUtils {

    /* maps the errors from BindingResult into a custom field errors container */
    public static ApiErrorsContainer map(BindingResult bindingResult){
        ApiErrorsContainer errorsContainer = new ApiErrorsContainer();
        if(bindingResult.hasErrors()){
            List<ApiError> apiErrors = new ArrayList<>();
            Map<String,ApiError> apiErrorsMap = new HashMap<>();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                apiErrorsMap.computeIfAbsent(fieldError.getField(),k -> {
                    ApiError apiError = new ApiError(fieldError.getField(),fieldError.getDefaultMessage());
                    apiErrors.add(apiError);
                    return apiError;
                });
                apiErrorsMap.computeIfPresent(fieldError.getField(),(k,v) -> {
                    v.addMessage(fieldError.getDefaultMessage());
                    return v;
                });
            });
            errorsContainer.setErrors(apiErrors);
        }
        return errorsContainer;
    }
}
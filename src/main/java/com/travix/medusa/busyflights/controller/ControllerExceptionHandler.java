package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.controller.validation.ApiErrorsContainer;
import com.travix.medusa.busyflights.controller.validation.InvalidApiRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    //handles the response to be sent back to the client after an api validation check fails
    @ExceptionHandler({ InvalidApiRequestException.class })
    public ResponseEntity<ApiErrorsContainer> handleInvalidRequestBody(InvalidApiRequestException exception) {
        return new ResponseEntity<>(exception.getApiErrorsContainer(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

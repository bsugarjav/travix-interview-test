package com.travix.medusa.busyflights.controller.validation;

/* this will be raised after an api validation check has occurred with errors found in the request. The exceptions handler will create the appropriate response */
public class InvalidApiRequestException extends RuntimeException {

    private ApiErrorsContainer apiErrorsContainer;

    public InvalidApiRequestException(ApiErrorsContainer apiErrorsContainer){
        this.apiErrorsContainer = apiErrorsContainer;
    }

    public ApiErrorsContainer getApiErrorsContainer() {
        return apiErrorsContainer;
    }
}

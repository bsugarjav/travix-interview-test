package com.travix.medusa.busyflights.controller.validation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* error messages container for a specific field */
public class ApiError {

    private String field;
    private Set<String> messages = new HashSet<>();

    public ApiError(){

    }

    public ApiError(String field, String message){
        if(Objects.isNull(field) || field.isEmpty()) {
            throw new IllegalArgumentException("field must be specified");
        }

        this.field = field;
        this.addMessage(message);
    }

    public void addMessage(String message){
        if(Objects.isNull(message) || message.isEmpty()) {
            throw new IllegalArgumentException("message must be specified");
        }

        this.messages.add(message);
    }

    public String getField() {
        return field;
    }

    public Set<String> getMessages() {
        return messages;
    }
}

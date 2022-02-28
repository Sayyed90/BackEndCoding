package com.example.etiqatest.ErrorHandler;

public class FieldErrorException extends RuntimeException {

    public FieldErrorException(String field) {
        super(String.format("Mandatory field: "+field+" is empty"));
    }
}

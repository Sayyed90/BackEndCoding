package com.example.etiqatest.ErrorHandler;

public class EntityWithIDNotFoundException  extends RuntimeException {

    public EntityWithIDNotFoundException(Long id,String entity) {
        super(String.format(entity+" with Id %d not found", id));
    }
}

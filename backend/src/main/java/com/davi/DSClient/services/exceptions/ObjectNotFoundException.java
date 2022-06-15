package com.davi.DSClient.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }
    public ObjectNotFoundException(Long id, Class<?> categoryClass) {
        super("Object not found. id: " + id + " to: " + categoryClass.getSimpleName());
    }
}

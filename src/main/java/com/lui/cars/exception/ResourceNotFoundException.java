package com.lui.cars.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, int id) {
        super(resource + " with ID " + id + " not found.");
    }
}

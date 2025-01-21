package com.example.c11_examensarbete.exceptionMappers;

public class ResourceNotFoundExceptionMapper extends RuntimeException{
    public ResourceNotFoundExceptionMapper(String message) {
        super(message);
    }
}

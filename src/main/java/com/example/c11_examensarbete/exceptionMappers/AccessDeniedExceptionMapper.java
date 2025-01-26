package com.example.c11_examensarbete.exceptionMappers;

public class AccessDeniedExceptionMapper extends RuntimeException{
    public AccessDeniedExceptionMapper(String message) {
        super(message);
    }
}

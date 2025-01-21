package com.example.c11_examensarbete.exceptionMappers;

public class BadRequestExceptionMapper extends RuntimeException{
    public BadRequestExceptionMapper(String message) {
        super(message);
    }
}

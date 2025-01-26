package com.example.c11_examensarbete.exceptionMappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundExceptionMapper.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundExceptionMapper ex) {
        logger.error("Error: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Resource Not Found",
                ex.getMessage(),
                "The resource you were looking for could not be found."
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestExceptionMapper.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestExceptionMapper ex) {
        logger.error("Error: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Bad Request",
                ex.getMessage(),
                "The request could not be processed due to invalid input."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        logger.error("Error: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Unauthorized",
                ex.getMessage(),
                "You need to log in to access this resource."
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedExceptionMapper.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedExceptionMapper ex) {
        logger.error("Error: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Forbidden",
                ex.getMessage(),
                "You do not have the necessary permissions to access this resource."
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Error: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal Server Error",
                ex.getMessage(),
                "Something went wrong on the server. Please try again later."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}


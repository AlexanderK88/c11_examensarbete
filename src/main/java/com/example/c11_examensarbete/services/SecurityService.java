package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.exceptionMappers.AccessDeniedExceptionMapper;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class SecurityService {

    public void validateUserAcces(String resourceOwnerId, String authenticatedOauthId) {
        if(!authenticatedOauthId.equals(resourceOwnerId)){
            throw new AccessDeniedExceptionMapper("You are no authorized to access this resource.");
        }
    }
}

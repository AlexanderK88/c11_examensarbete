package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.User;

import java.time.Instant;

public record UserDto(
        int id,
        String username,
        String email,
        String role,
        String profilePictureUrl,
        Instant createdAt) {
    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getProfilePictureUrl(),
                user.getCreatedAt()

        );
    }
}

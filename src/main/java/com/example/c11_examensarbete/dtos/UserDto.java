package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.User;

public record UserDto(
        String username,
        String email,
        String role
) {
    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}

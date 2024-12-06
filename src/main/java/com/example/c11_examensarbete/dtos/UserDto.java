package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.User;

public record UserDto(
        String username,
        String password,
        String email,
        String role
) {
    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }
}

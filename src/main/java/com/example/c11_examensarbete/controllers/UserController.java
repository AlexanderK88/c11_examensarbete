package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.c11_examensarbete.dtos.UserDto;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<UserDto> user = userService.getAllUsers();
        return user;
    }

    @GetMapping("/user/{id}")
    public List<UserDto> getUsersById(@PathVariable int id) {
        List<UserDto> user = userService.getUsersById(id);
        return user;
    }

    @PostMapping("/user/")
    public ResponseEntity<Void> AddUser(@RequestBody UserDto userDto) {
        int id = userService.addUser(userDto);
        return ResponseEntity.created(URI.create("/user/" + id)).build();
    }
}

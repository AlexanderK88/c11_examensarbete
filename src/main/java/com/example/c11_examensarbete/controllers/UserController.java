package com.example.c11_examensarbete.controllers;


import com.example.c11_examensarbete.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.example.c11_examensarbete.dtos.UserDto;

import java.util.List;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Map<String, Object> user(
            @AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }




    //TODO: Works in bruno but no exeption handling
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<UserDto> user = userService.getAllUsers();
        return user;
    }

    @GetMapping("/user/data/{oauthId}")
    public UserDto getUsersByOauthId(@PathVariable String oauthId) {
       UserDto user = userService.getUsersByOauthId(oauthId);
        return user;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/user/{id}")
    public List<UserDto> getUsersById(@PathVariable int id) {
        List<UserDto> user = userService.getUsersById(id);
        return user;
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/user")
    public ResponseEntity<Void> AddUser(@RequestBody UserDto userDto,
                                        @RequestBody String oauthId,
                                        @RequestBody String source
    ) {
        int id = userService.addUser(userDto, oauthId, source);
        return ResponseEntity.created(URI.create("/user/" + id)).build();
    }
}

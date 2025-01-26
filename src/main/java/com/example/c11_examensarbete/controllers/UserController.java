package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Map<String, Object> getUser(
            @AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> user = userService.getAllUsers();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/data/{oauthId}")
    public ResponseEntity<UserDto> getUsersByOauthId(@PathVariable String oauthId) {
       UserDto user = userService.getUsersByOauthId(oauthId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UserDto>> getUsersById(@PathVariable int id) {
        List<UserDto> user = userService.getUsersById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public ResponseEntity<Void> AddUser(@RequestBody UserDto userDto,
                                        @RequestBody String oauthId,
                                        @RequestBody String source
    ) {
        int id = userService.addUser(userDto, oauthId, source);
        return ResponseEntity.created(URI.create("/user/" + id)).build();
    }

    @PatchMapping("/user/add-info")
    public ResponseEntity<Void> AddUserInfo(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = authentication.getName();
        userService.addUserInfo(userDto, oauthId);
        return ResponseEntity.noContent().build();

    }
}

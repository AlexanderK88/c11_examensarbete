package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.UserDto;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public List<UserDto> getUsersById(int id){
        return userRepository.findById(id).stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public int addUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.email());
        user.setUsername(userDto.username());
        user.setPassword(userDto.password());
        user.setRole(userDto.role());
        userRepository.save(user);
        return user.getId();
    }
}

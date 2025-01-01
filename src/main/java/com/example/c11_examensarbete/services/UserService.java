package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.UserDto;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public UserDto getUsersByOauthId(String oauthId){
        User user = userRepository.findByOauthProviderId(oauthId);
        return UserDto.fromUser(user);
    }

    public List<UserDto> getUsersById(int id){
        return userRepository.findById(id).stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public int addUser(UserDto userDto, String oauthId, String source) {
        User user = new User();
        user.setEmail(userDto.email());
        user.setUsername(userDto.username());
        user.setRole(userDto.role());
        user.setOauthProviderId(oauthId); // Save OAuth ID
        user.setOauthProvider(source); // Save registration source (e.g., GITHUB)
        user.setCreatedAt(Instant.now()); // Set created timestamp
        userRepository.save(user);
        return user.getId();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserDto getUserDtoByUsername(String username){
        return UserDto.fromUser(userRepository.findByUsername(username));
    }

    public int updateUser(User user){
        return userRepository.save(user).getId();
    }


}

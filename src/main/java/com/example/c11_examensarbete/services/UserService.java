package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.UserDto;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
import com.example.c11_examensarbete.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    SecurityService securityService;

    public UserService(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public UserDto getUsersByOauthId(String oauthId){
        User user = userRepository.findByOauthProviderId(oauthId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("No user found with that id") );
        if(user == null){
            throw new ResourceNotFoundExceptionMapper("User not found with oauthId: " + oauthId);
        }
        return UserDto.fromUser(user);
    }

    public List<UserDto> getUsersById(int id){

       List<UserDto> users = userRepository.findById(id).stream()
                .map(UserDto::fromUser)
                .toList();
       if(users.isEmpty())
           throw new ResourceNotFoundExceptionMapper("User not found with id: " + id);
       return users;
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

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserDto getUserDtoByUsername(String username){
        return UserDto.fromUser(userRepository.findByUsername(username));
    }

    public int updateUser(User user){
        return userRepository.save(user).getId();
    }

    @Transactional
    public void addUserInfo(UserDto userdto, String oauthId) {
        User user = userRepository.findByOauthProviderId(oauthId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("No user found with oauthId: " + oauthId));

        securityService.validateUserAcces(user.getOauthProviderId(), oauthId);

        if (!userdto.email().isBlank()) user.setEmail(userdto.email());
        if (!userdto.username().isBlank()) user.setUsername(userdto.username());
        userRepository.save(user);
    }
}

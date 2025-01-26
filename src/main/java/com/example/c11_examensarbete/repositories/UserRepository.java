package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);

    Optional<User> findByOauthProviderId(String oauthId);
}

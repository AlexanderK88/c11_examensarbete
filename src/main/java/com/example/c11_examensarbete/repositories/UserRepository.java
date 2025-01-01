package com.example.c11_examensarbete.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.c11_examensarbete.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);

    User findByOauthProviderId(String oauthId);
}

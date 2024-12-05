package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}

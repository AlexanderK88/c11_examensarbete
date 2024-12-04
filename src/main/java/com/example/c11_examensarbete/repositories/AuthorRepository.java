package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}

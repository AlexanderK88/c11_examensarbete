package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Integer> {
}

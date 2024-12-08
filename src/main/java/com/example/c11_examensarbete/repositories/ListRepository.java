package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.List;
import org.springframework.data.repository.ListCrudRepository;

public interface ListRepository extends ListCrudRepository<List, Integer> {
}

package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.Review;
import org.springframework.data.repository.ListCrudRepository;

public interface ReviewRepository extends ListCrudRepository<Review, Integer> {
}

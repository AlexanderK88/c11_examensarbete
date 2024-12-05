package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.Comment;
import org.springframework.data.repository.ListCrudRepository;

public interface CommentRepository extends ListCrudRepository<Comment, Integer> {
}

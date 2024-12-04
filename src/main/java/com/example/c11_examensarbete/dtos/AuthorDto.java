package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Author;

public record AuthorDto(Integer id, String name) {
    public static AuthorDto fromAuthor(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName()
        );
    }
}
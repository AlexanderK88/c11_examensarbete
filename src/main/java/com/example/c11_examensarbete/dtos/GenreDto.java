package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Genre;

public record GenreDto(Integer id, String name) {

    public static GenreDto fromGenre(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName()
        );
    }
}
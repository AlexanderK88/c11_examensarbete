package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.SavedManga;

public record SavedMangaDto(
        int manga_id,
        int user_id
) {
    public static SavedMangaDto fromSavedManga(SavedManga savedManga){
        return new SavedMangaDto(
                savedManga.getManga().getId(),
                savedManga.getUser().getId()
        );
    }
}

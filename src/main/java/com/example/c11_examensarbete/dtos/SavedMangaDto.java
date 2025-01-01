package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.SavedManga;

public record SavedMangaDto(
        int mangaid,
        int userid,
        Float score,
        Integer chaptersRead,
        String status
) {
    public static SavedMangaDto fromSavedManga(SavedManga savedManga) {
        return new SavedMangaDto(
                savedManga.getManga().getId(),
                savedManga.getUser().getId(),
                savedManga.getPersonalRating(),
                savedManga.getCurrentChapter(),
                savedManga.getStatus()
        );
    }
}


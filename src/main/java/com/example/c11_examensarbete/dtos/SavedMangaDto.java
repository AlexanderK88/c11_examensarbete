package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.SavedManga;

public record SavedMangaDto(
        int id,
        int mangaid,
        int userid,
        Float score,
        Integer chaptersRead,
        String status,
        String title
) {
    public static SavedMangaDto fromSavedManga(SavedManga savedManga) {
        return new SavedMangaDto(
                savedManga.getId(),
                savedManga.getManga().getId(),
                savedManga.getUser().getId(),
                savedManga.getPersonalRating(),
                savedManga.getCurrentChapter(),
                savedManga.getStatus(),
                savedManga.getManga().getTitle()
        );
    }
}


package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.List;

public record ListDto(
        int id,
        String listName,
        int userId,
        java.util.List<SavedMangaDto> savedMangas) {
    public static ListDto fromList(List list) {
        return new ListDto(
                list.getId(),
                list.getListName(),
                list.getUser().getId(),
                list.getSavedMangas().stream().map(SavedMangaDto::fromSavedManga).toList()
        );
    }
}

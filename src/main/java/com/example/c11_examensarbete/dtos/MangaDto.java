package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Manga;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record MangaDto(
        int id,
        String title,
        String titleEnglish,
        String titleJapanese,
        String type,
        Integer volumes,
        Integer chapters,
        String status,
        String synopsis,
        Boolean publishing,
        Integer popularity,
        Integer ranking,
        BigDecimal score,
        Integer scoredBy,
        Instant publishedFrom,
        Instant publishedTo,
        List<ImageDto> images,
        List<AuthorDto> authors,
        List<GenreDto> genres
) {
    public static MangaDto fromManga(Manga manga) {
        return new MangaDto(
                manga.getId(),
                manga.getTitle(),
                manga.getTitleEnglish(),
                manga.getTitleJapanese(),
                manga.getType(),
                manga.getVolumes(),
                manga.getChapters(),
                manga.getStatus(),
                manga.getSynopsis(),
                manga.getPublishing(),
                manga.getPopularity(),
                manga.getRanking(),
                manga.getScore(),
                manga.getScoredBy(),
                manga.getPublishedFrom(),
                manga.getPublishedTo(),
                manga.getImages().stream().map(ImageDto::fromImage).toList(),
                manga.getAuthors().stream().map(AuthorDto::fromAuthor).toList(),
                manga.getGenres().stream().map(GenreDto::fromGenre).toList()
        );
    }
}
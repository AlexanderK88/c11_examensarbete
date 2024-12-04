package com.example.c11_examensarbete.dtos;

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
) {}
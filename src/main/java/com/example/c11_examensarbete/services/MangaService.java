package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.repositories.MangaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MangaService {

    MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public List<MangaDto> getAllMangas() {
        return mangaRepository.findAll().stream()
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 25);
    }

    public List<MangaDto> getMangaById(int id) {
        return mangaRepository.findById(id).stream()
                .map(MangaDto::fromManga)
                .toList();
    }

    public List<MangaDto> getMangaByCategory(int id) {
        return mangaRepository.findAll().stream()
                .filter(manga -> manga.getGenres().stream().anyMatch(genre -> genre.getId() == id))
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 25);
    }

    public List<MangaDto> getMangaByAuthor(int id) {
        return mangaRepository.findAll().stream()
                .filter(manga -> manga.getAuthors().stream().anyMatch(author -> author.getId() == id))
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 25);
    }

    public List<MangaDto> getNewManga() {
        Instant sixtyDaysAgo = Instant.now().minus(2000, ChronoUnit.DAYS); // change amount of days when we have every manga

        return mangaRepository.findAll().stream()
                .filter(manga -> {
                    Instant publishedFrom = manga.getPublishedFrom();
                    return publishedFrom != null && publishedFrom.isAfter(sixtyDaysAgo);
                })
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 10);
    }

    public List<MangaDto> getPopularManga() {
        return mangaRepository.findAll().stream()
                .sorted((m1, m2) -> m2.getPopularity().compareTo(m1.getPopularity()))
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 10);
    }

    public List<MangaDto> getMostReadManga() {
        return mangaRepository.findAll().stream()
                .sorted((m1, m2) -> m2.getScoredBy().compareTo(m1.getScoredBy()))
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 10);

    }

    public List<MangaDto> getHighestRatedManga() {
        return mangaRepository.findAll().stream()
                .sorted((m1, m2) -> {
                    BigDecimal score1 = m1.getScore() != null ? m1.getScore() : BigDecimal.ZERO;
                    BigDecimal score2 = m2.getScore() != null ? m2.getScore() : BigDecimal.ZERO;
                    return score2.compareTo(score1);
                })
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 10);
    }


}

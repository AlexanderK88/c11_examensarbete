package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.AuthorDto;
import com.example.c11_examensarbete.dtos.GenreDto;
import com.example.c11_examensarbete.dtos.ImageDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.repositories.ImageRepository;
import com.example.c11_examensarbete.repositories.MangaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MangaService {

    MangaRepository mangaRepository;
    ImageRepository imageRepository;

    public MangaService(MangaRepository mangaRepository, ImageRepository imageRepository) {
        this.mangaRepository = mangaRepository;
        this.imageRepository = imageRepository;
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

    public List<MangaDto> getMangaByGenre(int id) {
        return mangaRepository.findAll().stream()
                .filter(manga -> manga.getGenres().stream().anyMatch(genre -> genre.getId() == id))
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 25);
    }

    public List<GenreDto> getGenreByManga(int id) {
        return mangaRepository.findById(id).stream()
                .flatMap(manga -> manga.getGenres().stream())
                .map(GenreDto::fromGenre)
                .toList();
    }

    public List<MangaDto> getMangaByAuthor(int id) {
        List<MangaDto> mangas = mangaRepository.findAll().stream()
                .filter(manga -> manga.getAuthors().stream().anyMatch(author -> author.getId() == id))
                .map(MangaDto::fromManga)
                .toList();
        return mangas.size() > 25 ? mangas.subList(0, 25) : mangas;
    }

    public List<AuthorDto> getAuthorsByManga(int id) {
        return mangaRepository.findById(id).stream()
                .flatMap(manga -> manga.getAuthors().stream())
                .map(AuthorDto::fromAuthor)
                .toList();
    }

    public List<MangaDto> getNewManga() {
        Instant sixtyDaysAgo = Instant.now().minus(20000, ChronoUnit.DAYS); // change amount of days when we have every manga

        return mangaRepository.findAll().stream()
                .filter(manga -> {
                    Instant publishedFrom = manga.getPublishedFrom();
                    return publishedFrom != null && publishedFrom.isAfter(sixtyDaysAgo);
                })
                .sorted((m1, m2) -> m2.getPublishedFrom().compareTo(m1.getPublishedFrom()))
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 10);
    }

    public List<MangaDto> getPopularManga() {
        return mangaRepository.findAll().stream()
                .sorted((m1, m2) -> {
                    int popularity1 = m1.getPopularity() != null ? m1.getPopularity() : 0;
                    int popularity2 = m2.getPopularity() != null ? m2.getPopularity() : 0;
                    return Integer.compare(popularity2, popularity1);
                })
                .map(MangaDto::fromManga)
                .toList()
                .subList(0, 10);
    }

    public List<MangaDto> getMostReadManga() {
        return mangaRepository.findAll().stream()
                .sorted((m1, m2) -> {
                    int reads1 = m1.getScoredBy() != null ? m1.getScoredBy() : 0;
                    int reads2 = m2.getScoredBy() != null ? m2.getScoredBy() : 0;
                    return Integer.compare(reads2, reads1);
                })
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

    public ImageDto getImagesByManga(int id) {
        return imageRepository.findByMangaId(id).stream()
                .map(ImageDto::fromImage)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No images found for manga with id: " + id));
    }
}

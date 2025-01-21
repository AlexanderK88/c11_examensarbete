package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.AuthorDto;
import com.example.c11_examensarbete.dtos.GenreDto;
import com.example.c11_examensarbete.dtos.ImageDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.entities.Manga;
import com.example.c11_examensarbete.exceptionMappers.BadRequestExceptionMapper;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
import com.example.c11_examensarbete.repositories.ImageRepository;
import com.example.c11_examensarbete.repositories.MangaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MangaService {

    MangaRepository mangaRepository;
    ImageRepository imageRepository;

    public MangaService(MangaRepository mangaRepository, ImageRepository imageRepository) {
        this.mangaRepository = mangaRepository;
        this.imageRepository = imageRepository;
    }

    public Page<MangaDto> getAllMangas(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestExceptionMapper("Page number must be >= 0 and size must be > 0.");
        }
        Pageable pageable = PageRequest.of(page, size);
        return mangaRepository.findAll(pageable)
                .map(MangaDto::fromManga);
    }

    public List<MangaDto> getMangaById(int id) {
        if (id <= 0) {
            throw new BadRequestExceptionMapper("Manga ID must be a positive integer.");
        }
        List<MangaDto> mangas = mangaRepository.findById(id).stream()
                .map(MangaDto::fromManga)
                .toList();

        if (mangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("Manga with ID " + id + " not found.");
        }
        return mangas;
    }

    public Page<MangaDto> getMangaByGenre(int id, int page, int size) {
        if (id <= 0) {
            throw new BadRequestExceptionMapper("Genre ID must be a positive integer.");
        }
        if (page < 0 || size <= 0) {
            throw new BadRequestExceptionMapper("Page number must be >= 0 and size must be > 0.");
        }
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Order.asc("popularity"))
        );
        Page<MangaDto> mangas = mangaRepository.findAllByGenresId(id, pageable)
                .map(MangaDto::fromManga);
        if (mangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No mangas found for genre with ID " + id);
        }
        return mangas;
    }

    public List<GenreDto> getGenreByManga(int id) {
        if (id <= 0) {
            throw new BadRequestExceptionMapper("Manga ID must be a positive integer.");
        }
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Manga with ID " + id + " not found."));
        return manga.getGenres().stream()
                .map(GenreDto::fromGenre)
                .toList();
    }

    public List<MangaDto> getMangaByAuthor(int id) {
        if (id <= 0) {
            throw new BadRequestExceptionMapper("Author ID must be a positive integer.");
        }
        List<MangaDto> mangas = mangaRepository.findAll().stream()
                .filter(manga -> manga.getAuthors().stream().anyMatch(author -> author.getId() == id))
                .map(MangaDto::fromManga)
                .toList();
        if (mangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No mangas found for author with ID " + id);
        }
        return mangas.size() > 25 ? mangas.subList(0, 25) : mangas;
    }

    public List<AuthorDto> getAuthorsByManga(int id) {
        if(id <= 0){
            throw new BadRequestExceptionMapper("Manga ID must be greater than 0.");
        }
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Manga with ID " + id + " not found."));
        return manga.getAuthors().stream()
                .map(AuthorDto::fromAuthor)
                .toList();
    }

    public List<MangaDto> getNewManga() {
        Instant sixtyDaysAgo = Instant.now().minus(20000, ChronoUnit.DAYS); // Adjust the days as needed
        List<MangaDto> newMangas = mangaRepository.findAll().stream()
                .filter(manga -> {
                    Instant publishedFrom = manga.getPublishedFrom();
                    return publishedFrom != null && publishedFrom.isAfter(sixtyDaysAgo);
                })
                .sorted((m1, m2) -> m2.getPublishedFrom().compareTo(m1.getPublishedFrom()))
                .map(MangaDto::fromManga)
                .toList();
        if (newMangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No new mangas found.");
        }
        return newMangas.subList(0, Math.min(newMangas.size(), 10));
    }

    public List<MangaDto> getPopularManga() {
        List<MangaDto> popularMangas = mangaRepository.findAll().stream()
                .sorted((m1, m2) -> {
                    int popularity1 = m1.getPopularity() != null ? m1.getPopularity() : 0;
                    int popularity2 = m2.getPopularity() != null ? m2.getPopularity() : 0;
                    return Integer.compare(popularity2, popularity1);
                })
                .map(MangaDto::fromManga)
                .toList();
        if (popularMangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No popular mangas found.");
        }
        return popularMangas.subList(0, Math.min(popularMangas.size(), 10));
    }

    public Page<MangaDto> getSortedManga(
            int page,
            int size,
            String sort,
            String sortDirection,
            List<String> types,
            String genre,
            String search) {

        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(new Sort.Order(direction, sort))
        );

        if (search != null && !search.isEmpty() && types != null && !types.isEmpty()) {
            return mangaRepository.findAllByTitleContainingAndTypeIn(search, types, pageable)
                    .map(MangaDto::fromManga);
        } else if (search != null && !search.isEmpty()) {
            return mangaRepository.findAllByTitleContaining(search, pageable)
                    .map(MangaDto::fromManga);
        } else if (types != null && !types.isEmpty() && genre != null && !genre.isEmpty()) {
            return mangaRepository.findAllByTypeInAndGenresName(types, genre, pageable)
                    .map(MangaDto::fromManga);
        } else if (types != null && !types.isEmpty()) {
            return mangaRepository.findAllByTypeIn(types, pageable)
                    .map(MangaDto::fromManga);
        } else if(genre != null && !genre.isEmpty()) {
            return mangaRepository.findAllByGenresName(genre, pageable)
                    .map(MangaDto::fromManga);
        }

        // Default case: No search or type filtering
        return mangaRepository.findAll(pageable)
                .map(MangaDto::fromManga);
    }

    public List<MangaDto> getMostReadManga() {
        List<MangaDto> mostReadMangas = mangaRepository.findAll().stream()
                .sorted((m1, m2) -> {
                    int reads1 = m1.getScoredBy() != null ? m1.getScoredBy() : 0;
                    int reads2 = m2.getScoredBy() != null ? m2.getScoredBy() : 0;
                    return Integer.compare(reads2, reads1);
                })
                .map(MangaDto::fromManga)
                .toList();
        if (mostReadMangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No most-read mangas found.");
        }
        return mostReadMangas.subList(0, Math.min(mostReadMangas.size(), 10));
    }

    public List<MangaDto> getHighestRatedManga() {
        List<MangaDto> highestRatedMangas = mangaRepository.findAll().stream()
                .sorted((m1, m2) -> {
                    BigDecimal score1 = m1.getScore() != null ? m1.getScore() : BigDecimal.ZERO;
                    BigDecimal score2 = m2.getScore() != null ? m2.getScore() : BigDecimal.ZERO;
                    return score2.compareTo(score1);
                })
                .map(MangaDto::fromManga)
                .toList();
        if (highestRatedMangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No highest-rated mangas found.");
        }
        return highestRatedMangas.subList(0, Math.min(highestRatedMangas.size(), 10));
    }

    public ImageDto getImagesByManga(int id) {
        if(id <= 0){
            throw new BadRequestExceptionMapper("Manga id must be greater than 0.");
        }
        return imageRepository.findByMangaId(id).stream()
                .map(ImageDto::fromImage)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("No images found for manga with ID " + id));
    }

    public List<ImageDto> getAllImages() {
        List<ImageDto> images = imageRepository.findAll().stream()
                .map(ImageDto::fromImage)
                .toList();
        if (images.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No images found.");
        }
        return images;
    }

    public Page<MangaDto> getMangaBySearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MangaDto> mangas = mangaRepository.findAllByTitleContaining(search, pageable)
                .map(MangaDto::fromManga);
        if (mangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No mangas found matching the search query: " + search);
        }
        return mangas;
    }

    public List<MangaDto> getMangaByIds(List<Integer> mangaIds) {
        if(mangaIds.isEmpty()){
            throw new BadRequestExceptionMapper("No manga IDs found");
        }
        List<Manga> mangas = mangaRepository.findAllById(mangaIds);
        if (mangas.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No mangas found for the given IDs.");
        }
        return mangas.stream()
                .map(MangaDto::fromManga)
                .collect(Collectors.toList());
    }
}

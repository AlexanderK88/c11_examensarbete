package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.services.MangaService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.example.c11_examensarbete.dtos.AuthorDto;
import com.example.c11_examensarbete.dtos.GenreDto;
import com.example.c11_examensarbete.dtos.ImageDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MangaController {

    MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/manga")
    public Page<MangaDto> getAllMangas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size
    ) {
        return mangaService.getAllMangas(page, size);
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/manga/{id}")
    public List<MangaDto> getMangaById(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaById(id);
        return manga;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/manga/genre/{id}")
    public Page<MangaDto> getMangaByGenre(@PathVariable int id,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "25") int size) {
        return mangaService.getMangaByGenre(id, page, size);
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/manga/genres/{id}")
    public List<GenreDto> getGenreByManga(@PathVariable int id) {
        List<GenreDto> genre = mangaService.getGenreByManga(id);
        return genre;
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/author/{id}")
    public List<MangaDto> getMangaByAuthor(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaByAuthor(id);
        return manga;
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/authors/{id}")
    public List<AuthorDto> getAuthorsByManga(@PathVariable int id) {
        List<AuthorDto> author = mangaService.getAuthorsByManga(id);
        return author;
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/new-releases")
    public List<MangaDto> getNewManga() {
        List<MangaDto> manga = mangaService.getNewManga();
        return manga;
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/popular")
    public List<MangaDto> getPopularManga() {
        List<MangaDto> manga = mangaService.getPopularManga();
        return manga;
    }

    @GetMapping("/manga/sorted")
    public Page<MangaDto> getPopularMangas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "popularity") String sort,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) List<String> types,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String search
    ) {
        return mangaService.getSortedManga(page, size, sort, sortDirection, types, genre, search );
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/most-read")
    public List<MangaDto> getMostReadManga() {
        List<MangaDto> manga = mangaService.getMostReadManga();
        return manga;
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/highest-rated")
    public List<MangaDto> getHighestRatedMangas() {
        List<MangaDto> manga = mangaService.getHighestRatedManga();
        return manga;
    }

    //Works in bruno but no exeption handling
    @GetMapping("/manga/images/{id}")
    public ImageDto getImagesByManga(@PathVariable int id) {
        ImageDto image = mangaService.getImagesByManga(id);
                return image;
    }

    @GetMapping("/manga/search")
    public Page<MangaDto> searchManga(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam String query
    ) {
        if (query.isBlank()) {
            return Page.empty();
        }
        return mangaService.getMangaBySearch(query, page, size);
    }
}

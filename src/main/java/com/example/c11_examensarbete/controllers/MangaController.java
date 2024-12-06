package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.services.MangaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.c11_examensarbete.dtos.AuthorDto;
import com.example.c11_examensarbete.dtos.GenreDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MangaController {

    MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping("/manga")
    public List<MangaDto> getAllMangas() {
        List<MangaDto> manga = mangaService.getAllMangas();
        return manga;
    }

    @GetMapping("/manga/{id}")
    public List<MangaDto> getMangaById(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaById(id);
        return manga;
    }

    @GetMapping("/manga/genre/{id}")
    public List<MangaDto> getMangaByGenre(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaByGenre(id);
        return manga;
    }

    @GetMapping("/manga/genres/{id}")
    public List<GenreDto> getGenreByManga(@PathVariable int id) {
        List<GenreDto> genre = mangaService.getGenreByManga(id);
        return genre;
    }

    @GetMapping("/manga/author/{id}")
    public List<MangaDto> getMangaByAuthor(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaByAuthor(id);
        return manga;
    }

    @GetMapping("/manga/authors/{id}")
    public List<AuthorDto> getAuthorsByManga(@PathVariable int id) {
        List<AuthorDto> author = mangaService.getAuthorsByManga(id);
        return author;
    }

    @GetMapping("/manga/new-releases")
    public List<MangaDto> getNewManga() {
        List<MangaDto> manga = mangaService.getNewManga();
        return manga;
    }

    @GetMapping("/manga/popular")
    public List<MangaDto> getPopularManga() {
        List<MangaDto> manga = mangaService.getPopularManga();
        return manga;
    }

    @GetMapping("/manga/most-read")
    public List<MangaDto> getMostReadManga() {
        List<MangaDto> manga = mangaService.getMostReadManga();
        return manga;
    }

    @GetMapping("/manga/highest-rated")
    public List<MangaDto> getHighestRatedMangas() {
        List<MangaDto> manga = mangaService.getHighestRatedManga();
        return manga;
    }
}

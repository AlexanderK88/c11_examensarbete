package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.services.MangaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MangaController {

    MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping("/api/manga")
    public List<MangaDto> getAllMangas() {
        List<MangaDto> manga = mangaService.getAllMangas();
        return manga;
    }

    @GetMapping("/api/manga/{id}")
    public List<MangaDto> getMangaById(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaById(id);
        return manga;
    }

    @GetMapping("/api/manga/category/{id}")
    public List<MangaDto> getMangaByCategory(@PathVariable int id) {
        List<MangaDto> manga = mangaService.getMangaByCategory(id);
        return manga;
    }

    @GetMapping("/api/manga/new-releases")
    public List<MangaDto> getNewManga() {
        List<MangaDto> manga = mangaService.getNewManga();
        return manga;
    }

    @GetMapping("/api/manga/popular")
    public List<MangaDto> getPopularManga() {
        List<MangaDto> manga = mangaService.getPopularManga();
        return manga;
    }

    @GetMapping("/api/manga/most-read")
    public List<MangaDto> getMostReadManga() {
        List<MangaDto> manga = mangaService.getMostReadManga();
        return manga;
    }

    @GetMapping("/api/manga/highest-rated")
    public List<MangaDto> getHighestRatedMangas() {
        List<MangaDto> manga = mangaService.getHighestRatedManga();
        return manga;
    }

}

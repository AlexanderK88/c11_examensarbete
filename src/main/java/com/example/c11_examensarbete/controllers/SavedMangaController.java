package com.example.c11_examensarbete.controllers;


import com.example.c11_examensarbete.dtos.CommentDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.services.MangaService;
import com.example.c11_examensarbete.services.SavedMangaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class SavedMangaController {

    private final MangaService mangaService;
    SavedMangaService savedMangaService;

    public SavedMangaController(SavedMangaService savedMangaService, MangaService mangaService) {
        this.savedMangaService = savedMangaService;
        this.mangaService = mangaService;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/user/{userid}/mangas")
    public List<MangaDto> getUsersSavedMangas(@PathVariable int userid) {
        // Fetch the SavedMangaDto for the user
        List<SavedMangaDto> savedMangas = savedMangaService.getUsersSavedMangas(userid);

        // Collect all the manga IDs from the SavedMangaDto list
        List<Integer> mangaIds = savedMangas.stream()
                .map(savedManga -> savedManga.mangaid())
                .collect(Collectors.toList());

        // Fetch all MangaDto objects for the collected manga IDs
        List<MangaDto> mangaDtos = mangaService.getMangaByIds(mangaIds);

        return mangaDtos;
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/user/manga")
    public ResponseEntity<Void> addSavedManga(@RequestBody SavedMangaDto savedMangaDto) {
        int id = savedMangaService.saveManga(savedMangaDto);
        return ResponseEntity.created(URI.create("/reviews/comments/" + id)).build();
    }

    //TODO: Works in bruno but no exeption handling
    @DeleteMapping("/user/manga/{savedmangaid}/{userid}")
    public ResponseEntity<Void> deleteSavedManga(@PathVariable int savedmangaid, @PathVariable int userid) {
        savedMangaService.deleteSavedManga(savedmangaid, userid);
        return ResponseEntity.noContent().build();
    }
}



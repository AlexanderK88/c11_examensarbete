package com.example.c11_examensarbete.controllers;


import com.example.c11_examensarbete.dtos.CommentDto;
import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.services.SavedMangaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SavedMangaController {

    SavedMangaService savedMangaService;

    public SavedMangaController(SavedMangaService savedMangaService) {
        this.savedMangaService = savedMangaService;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/user/{userid}/mangas")
    public List<SavedMangaDto> getUsersSavedMangas(@PathVariable int userid) {
        List<SavedMangaDto> manga = savedMangaService.getUsersSavedMangas(userid);
        return manga;
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/user/{userid}/manga")
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



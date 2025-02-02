package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
import com.example.c11_examensarbete.repositories.UserRepository;
import com.example.c11_examensarbete.services.MangaService;
import com.example.c11_examensarbete.services.SavedMangaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class SavedMangaController {

    private final MangaService mangaService;
    private final UserRepository userRepository;
    SavedMangaService savedMangaService;

    public SavedMangaController(SavedMangaService savedMangaService, MangaService mangaService, UserRepository userRepository) {
        this.savedMangaService = savedMangaService;
        this.mangaService = mangaService;
        this.userRepository = userRepository;
    }

    //TODO: Works in bruno but no exception handling
    @GetMapping("/user/mangas")
    public ResponseEntity<List<MangaDto>> getUsersMangas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = authentication.getName();



        List<SavedMangaDto> savedMangas = savedMangaService.getUsersSavedMangas(oauthId);

        List<Integer> mangaIds = savedMangas.stream()
                .map(SavedMangaDto::mangaid)
                .collect(Collectors.toList());

        List<MangaDto> mangaDtos = mangaService.getMangaByIds(mangaIds);

        return ResponseEntity.ok(mangaDtos);
    }

    @GetMapping("/user/savedmanga")
    public ResponseEntity<List<SavedMangaDto>> getUsersSavedManga() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = authentication.getName();
        return ResponseEntity.ok(savedMangaService.getUsersSavedMangas(oauthId));
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/user/manga")
    public ResponseEntity<Void> addSavedManga(@RequestBody SavedMangaDto savedMangaDto) {
        int id = savedMangaService.saveManga(savedMangaDto);
        return ResponseEntity.created(URI.create("/reviews/comments/" + id)).build();
    }

    @PutMapping("/user/manga/edit")
    public ResponseEntity<Void> editSavedManga(@RequestBody SavedMangaDto savedMangaDto) {
        int id = savedMangaService.editManga(savedMangaDto);
        return ResponseEntity.created(URI.create("/reviews/comments/" + id)).build();
    }


    //TODO: Works in bruno but no exeption handling
    @DeleteMapping("/user/manga/{savedmangaid}")
    public ResponseEntity<Void> deleteSavedManga(@PathVariable int savedmangaid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = authentication.getName();
        savedMangaService.deleteSavedManga(savedmangaid, oauthId);
        return ResponseEntity.noContent().build();
    }
}

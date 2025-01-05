package com.example.c11_examensarbete.services;



import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.entities.SavedManga;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.example.c11_examensarbete.repositories.SavedMangaRepository;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SavedMangaService {

    MangaRepository mangaRepository;
    SavedMangaRepository savedMangaRepository;
    UserRepository userRepository;

    public SavedMangaService(SavedMangaRepository savedMangaRepository, MangaRepository mangaRepository, UserRepository userRepository) {
        this.savedMangaRepository = savedMangaRepository;
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
    }

    public List<SavedMangaDto> getUsersSavedMangas(int userid){
        return savedMangaRepository.findAll().stream()
                .filter(savedManga -> savedManga.getUser().getId() == userid)
                .map(SavedMangaDto::fromSavedManga)
                .toList();
    }

    public int saveManga(SavedMangaDto savedMangaDto) {
        System.out.println(savedMangaDto.mangaid());
        SavedManga savedManga = new SavedManga();
        savedManga.setManga(mangaRepository.findById(savedMangaDto.mangaid())
                .orElseThrow(() -> new NoSuchElementException("Manga not found with id: " + savedMangaDto.mangaid())));
        savedManga.setUser(userRepository.findById(savedMangaDto.userid())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + savedMangaDto.userid())));

        savedManga.setStatus(savedMangaDto.status());
        savedManga.setPersonalRating(savedMangaDto.score());
        savedManga.setCurrentChapter(savedMangaDto.chaptersRead());
        savedManga.setMangaTitle(savedMangaDto.title());
        savedMangaRepository.save(savedManga);
        return savedManga.getId();
    }

    public void deleteSavedManga(int savedMangaId, int userId) {
        SavedManga savedManga = savedMangaRepository.findByMangaId(savedMangaId).stream()
                .filter(savedManga1 -> savedManga1.getUser().getId() == userId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Saved manga not found with id: " + savedMangaId));
        savedMangaRepository.delete(savedManga);
    }
}

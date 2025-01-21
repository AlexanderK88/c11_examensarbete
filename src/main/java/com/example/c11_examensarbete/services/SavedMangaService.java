package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.entities.SavedManga;
import com.example.c11_examensarbete.exceptionMappers.BadRequestExceptionMapper;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.example.c11_examensarbete.repositories.SavedMangaRepository;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(userid <= 0){
            throw new BadRequestExceptionMapper("User id must be greater than 0");
        }
        return savedMangaRepository.findAll().stream()
                .filter(savedManga -> savedManga.getUser().getId() == userid)
                .map(SavedMangaDto::fromSavedManga)
                .toList();
    }

    public int saveManga(SavedMangaDto savedMangaDto) {
        if(savedMangaDto == null) {
            throw new BadRequestExceptionMapper("SavedMangaDto is null");
        }
        System.out.println(savedMangaDto.mangaid());
        SavedManga savedManga = new SavedManga();
        savedManga.setManga(mangaRepository.findById(savedMangaDto.mangaid())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Manga not found with id: " + savedMangaDto.mangaid() + ". Unable to save manga.")));
        savedManga.setUser(userRepository.findById(savedMangaDto.userid())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User not found with id: " + savedMangaDto.userid() + ". Unable to associate user with saved manga.")));

        savedManga.setStatus(savedMangaDto.status());
        savedManga.setPersonalRating(savedMangaDto.score());
        savedManga.setCurrentChapter(savedMangaDto.chaptersRead());
        savedManga.setMangaTitle(savedMangaDto.title());
        savedMangaRepository.save(savedManga);
        return savedManga.getId();
    }

    public int editManga(SavedMangaDto savedMangaDto) {
        if(savedMangaDto == null) {
            throw new BadRequestExceptionMapper("SavedMangaDto is null");
        }
        System.out.println("MangaId: " + savedMangaDto.mangaid());
        System.out.println("UserId: " + savedMangaDto.userid());

        List<SavedManga> mangaList = savedMangaRepository.findByMangaId(savedMangaDto.mangaid());
        if (mangaList.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No manga found with mangaId: " + savedMangaDto.mangaid());
        }

        mangaList.forEach(manga -> System.out.println("Found manga: " + manga));

        SavedManga savedManga = mangaList.stream()
                .filter(savedManga1 -> savedManga1.getUser().getId().equals(savedMangaDto.userid()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Saved manga not found with id: " + savedMangaDto.mangaid() + " and user id: " + savedMangaDto.userid()));

        savedManga.setStatus(savedMangaDto.status());
        savedManga.setPersonalRating(savedMangaDto.score());
        savedManga.setCurrentChapter(savedMangaDto.chaptersRead());

        SavedManga updatedManga = savedMangaRepository.save(savedManga);
        System.out.println("Updated manga: " + updatedManga);

        return savedManga.getId();
    }

    public void deleteSavedManga(int savedMangaId, int userId) {
        if(savedMangaId <= 0 || userId <= 0){
            throw new BadRequestExceptionMapper("SavedMangaId or UserId is less than 0");
        }
        SavedManga savedManga = savedMangaRepository.findByMangaId(savedMangaId).stream()
                .filter(savedManga1 -> savedManga1.getUser().getId() == userId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Saved manga not found with id: " + savedMangaId));
        savedMangaRepository.delete(savedManga);
    }
}

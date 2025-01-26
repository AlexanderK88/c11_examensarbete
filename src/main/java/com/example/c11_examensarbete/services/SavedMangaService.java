package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.entities.SavedManga;
import com.example.c11_examensarbete.entities.User;
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
    SecurityService securityService;

    public SavedMangaService(SavedMangaRepository savedMangaRepository, MangaRepository mangaRepository, UserRepository userRepository, SecurityService securityService) {
        this.savedMangaRepository = savedMangaRepository;
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public List<SavedMangaDto> getUsersSavedMangas(String oauthId){
        if(oauthId == null || oauthId.isBlank()){
            throw new BadRequestExceptionMapper("Id must exist");
        }

        User user = userRepository.findByOauthProviderId(oauthId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User not found"));

        securityService.validateUserAcces(user.getOauthProviderId(), oauthId);

        return savedMangaRepository.findAll().stream()
                .filter(savedManga -> savedManga.getUser().getId().equals(user.getId()))
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

    public void deleteSavedManga(int savedMangaId, String oauthId) {
        if(savedMangaId <= 0 || oauthId == null || oauthId.isBlank()){
            throw new BadRequestExceptionMapper("SavedMangaId or auth is not valid");
        }
        User user = userRepository.findByOauthProviderId(oauthId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User not found"));
        securityService.validateUserAcces(user.getOauthProviderId(), oauthId);

        SavedManga savedManga = savedMangaRepository.findByMangaId(savedMangaId).stream()
                .filter(savedManga1 -> savedManga1.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Saved manga not found with id: " + savedMangaId));
        savedMangaRepository.delete(savedManga);
    }
}

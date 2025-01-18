package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.entities.List;
import com.example.c11_examensarbete.dtos.ListDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.entities.SavedManga;
import com.example.c11_examensarbete.repositories.ListRepository;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.example.c11_examensarbete.repositories.SavedMangaRepository;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ListService {

    UserRepository userRepository;
    ListRepository listRepository;
    MangaRepository mangaRepository;
    SavedMangaRepository savedMangaRepository;

    public ListService (ListRepository listRepository, MangaRepository mangaRepository, UserRepository userRepository, SavedMangaRepository savedMangaRepository) {
        this.listRepository = listRepository;
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
        this.savedMangaRepository = savedMangaRepository;
    }

    public java.util.List<ListDto> getAllListsByUser(int id) {
        return listRepository.findAll().stream()
                .filter(list -> list.getUser().getId() == id)
                .map(ListDto::fromList)
                .toList();
    }

    public java.util.List<SavedMangaDto> getAllSavedMangasInList(int id) {
        return listRepository.findById(id).stream()
                .flatMap(list -> list.getSavedMangas().stream())
                .map(SavedMangaDto::fromSavedManga)
                .toList();
    }

    public java.util.List<MangaDto> getMangasFromSavedMangas(int listId) {
        java.util.List<Integer> mangaIds = listRepository.findById(listId).stream()
                .flatMap(list -> list.getSavedMangas().stream())
                .map(savedManga -> savedManga.getManga().getId())
                .collect(Collectors.toList());

        return mangaRepository.findAllById(mangaIds).stream()
                .map(MangaDto::fromManga)
                .collect(Collectors.toList());
    }

    public int addList(ListDto listDto) {
        List list = new List();
        list.setListName(listDto.listName());
        list.setUser(userRepository.findById(listDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + listDto.userId())));

        listRepository.save(list);

        return list.getId();

    }

    public int addSavedMangaToList(int listId, int savedMangaId) {
        List list = listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("List not found with id: " + listId));
        SavedManga savedManga = savedMangaRepository.findByMangaId(savedMangaId).stream()
                .filter(savedManga1 -> Objects.equals(savedManga1.getUser().getId(), list.getUser().getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("SavedManga not found with id: " + savedMangaId));
        list.getSavedMangas().add(savedManga);
        listRepository.save(list);

        return list.getId();
    }
    public void deleteList(int id) {
        listRepository.deleteById(id);
    }
}


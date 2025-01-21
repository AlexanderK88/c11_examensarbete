package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.entities.List;
import com.example.c11_examensarbete.dtos.ListDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.entities.SavedManga;
import com.example.c11_examensarbete.exceptionMappers.BadRequestExceptionMapper;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
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
        if(id <= 0){
            throw new BadRequestExceptionMapper("ID must be greater than 0.");
        }
        return listRepository.findAll().stream()
                .filter(list -> list.getUser().getId() == id)
                .map(ListDto::fromList)
                .toList();
    }

    public java.util.List<SavedMangaDto> getAllSavedMangasInList(int id) {
        if (id <= 0){
            throw new BadRequestExceptionMapper("ID must be greater than 0.");
        }
        return listRepository.findById(id).stream()
                .flatMap(list -> list.getSavedMangas().stream())
                .map(SavedMangaDto::fromSavedManga)
                .toList();
    }

    public java.util.List<MangaDto> getMangasFromSavedMangas(int listId) {
        if(listId <= 0){
            throw new BadRequestExceptionMapper("ID must be greater than 0.");
        }
        java.util.List<Integer> mangaIds = listRepository.findById(listId).stream()
                .flatMap(list -> list.getSavedMangas().stream())
                .map(savedManga -> savedManga.getManga().getId())
                .collect(Collectors.toList());

        return mangaRepository.findAllById(mangaIds).stream()
                .map(MangaDto::fromManga)
                .collect(Collectors.toList());
    }

    public int addList(ListDto listDto) {
        if(listDto == null){
            throw new BadRequestExceptionMapper("ListDto is null.");
        }
        List list = new List();
        list.setListName(listDto.listName());
        list.setUser(userRepository.findById(listDto.userId())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User not found with id: " + listDto.userId())));

        listRepository.save(list);

        return list.getId();

    }

    public int addSavedMangaToList(int listId, int savedMangaId) {
        if(listId <= 0 || savedMangaId <= 0){
            throw new BadRequestExceptionMapper("Both IDs must be greater than 0.");
        }
        List list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("List not found with id: " + listId));
        SavedManga savedManga = savedMangaRepository.findByMangaId(savedMangaId).stream()
                .filter(savedManga1 -> Objects.equals(savedManga1.getUser().getId(), list.getUser().getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("SavedManga not found with id: " + savedMangaId));
        list.getSavedMangas().add(savedManga);
        listRepository.save(list);

        return list.getId();
    }
    public void deleteList(int id) {
        if (id <= 0){
            throw new BadRequestExceptionMapper("ID must be greater than 0.");
        }
        listRepository.deleteById(id);
    }

    public void deleteSavedMangaFromList(int listId, int mangaId) {
        if(listId <= 0 || mangaId <= 0){
            throw new BadRequestExceptionMapper("Both IDs must be greater than 0.");
        }
        List list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("List not found with id: " + listId));
        list.getSavedMangas().removeIf(savedManga -> Objects.equals(savedManga.getId(), mangaId));
        listRepository.save(list);
    }
}


package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.SavedManga;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface SavedMangaRepository extends ListCrudRepository<SavedManga, Integer> {
    List<SavedManga> findByMangaId(Integer manga_id);
}

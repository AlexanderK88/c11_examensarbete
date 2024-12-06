package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.entities.SavedManga;
import org.springframework.data.repository.ListCrudRepository;

public interface SavedMangaRepository extends ListCrudRepository<SavedManga, Integer> {
}

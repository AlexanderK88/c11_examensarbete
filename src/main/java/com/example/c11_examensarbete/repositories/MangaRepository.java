package com.example.c11_examensarbete.repositories;
import com.example.c11_examensarbete.entities.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface MangaRepository extends ListCrudRepository<Manga, Integer> {
    Page<Manga> findAll(Pageable pageable);
    Page<Manga> findAllByTypeIn(List<String> types, Pageable pageable);
    Page<Manga> findAllByTitleContaining(String title, Pageable pageable);
    Page<Manga> findAllByTitleContainingAndTypeIn(String search, List<String> types, Pageable pageable);

    Page<Manga> findAllByGenresId(int id, Pageable pageable);

    Page<Manga> findAllByTypeInAndGenresName(List<String> types, String genre, Pageable pageable);

    Page<Manga> findAllByGenresName(String genre, Pageable pageable);
}


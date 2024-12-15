package com.example.c11_examensarbete.repositories;
import com.example.c11_examensarbete.entities.Genre;
import com.example.c11_examensarbete.entities.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface MangaRepository extends ListCrudRepository<Manga, Integer> {
    Page<Manga> findAll(Pageable pageable);
    Page<Manga> findAllByTypeIn(List<String> types, Pageable pageable);
    Page<Manga> findAllByTitleContaining(String title, Pageable pageable);
    Page<Manga> findAllByTitleContainingAndTypeIn(String search, List<String> types, Pageable pageable);

    Page<Manga> findAllByGenresId(int id, Pageable pageable);
}

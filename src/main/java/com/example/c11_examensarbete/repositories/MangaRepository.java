package com.example.c11_examensarbete.repositories;
import com.example.c11_examensarbete.entities.Manga;
import org.springframework.data.repository.ListCrudRepository;



public interface MangaRepository extends ListCrudRepository<Manga, Integer> {

}

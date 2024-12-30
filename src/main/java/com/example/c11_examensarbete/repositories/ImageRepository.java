package com.example.c11_examensarbete.repositories;

import com.example.c11_examensarbete.dtos.ImageDto;
import com.example.c11_examensarbete.entities.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ImageRepository extends ListCrudRepository<Image, Integer> {
    List<Image> findByMangaId(Integer manga_id);
}

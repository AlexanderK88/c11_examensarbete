package com.example.c11_examensarbete.controllers;


import com.example.c11_examensarbete.services.MangaWebApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MangaWebApiController {


    @Autowired
    private MangaWebApiService mangaWebApiService;

    @GetMapping("/remote/api/manga")
    public String getManga() {
        return mangaWebApiService.getManga();
    }

    @GetMapping("/remote/api/manga/fetch-all")
    public void fetchAllMangas() {
        mangaWebApiService.fetchAndSaveMangas();
    }
}

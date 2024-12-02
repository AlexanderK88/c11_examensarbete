package com.example.c11_examensarbete.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;


@Service
public class MangaWebApiService {

    private final RestClient restClient;

    public MangaWebApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getManga() {
        return restClient
                .get()
                .uri("https://api.jikan.moe/v4/manga")
                .retrieve()
                .body(String.class);
    }

    public String getEveryPage() {
        List<String> allPages = new ArrayList<>();
        for (int i = 1; i < 2930; i++) {
            String pageData = restClient
                    .get()
                    .uri("https://api.jikan.moe/v4/manga?page=" + i)
                    .retrieve()
                    .body(String.class);
            allPages.add(pageData);
        }
        return String.join(",", allPages);
    }
}

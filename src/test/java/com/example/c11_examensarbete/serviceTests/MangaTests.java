package com.example.c11_examensarbete.serviceTests;

import com.example.c11_examensarbete.config.SecurityConfig;
import com.example.c11_examensarbete.controllers.MangaController;
import com.example.c11_examensarbete.dtos.AuthorDto;
import com.example.c11_examensarbete.dtos.GenreDto;
import com.example.c11_examensarbete.dtos.ImageDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.entities.Manga;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.example.c11_examensarbete.services.MangaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;
@WebMvcTest(controllers = MangaController.class)
@EnableMethodSecurity(securedEnabled = false)
public class MangaTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MangaService mangaService;

    @Test
    @DisplayName("Should get a manga with legit id")
    void shouldGetMangaWithLegitId() throws Exception {

        MangaDto mangaDto = new MangaDto(
                1, // id
                "Monster", // title
                "Monster (English)", // titleEnglish
                "モンスター", // titleJapanese
                "Manga", // type
                18, // volumes
                162, // chapters
                "Finished", // status
                "Dr. Tenma risks everything to stop a serial killer he once saved.", // synopsis
                false, // publishing
                15, // popularity
                5, // ranking
                new BigDecimal("9.2"), // score
                20000, // scoredBy
                Instant.parse("1994-12-10T00:00:00Z"), // publishedFrom
                Instant.parse("2001-12-10T00:00:00Z"), // publishedTo
                List.of(new ImageDto(1, "123", "12414", "124124", 1), new ImageDto(1, "123", "12414", "124124", 1)), // images
                List.of(new AuthorDto(1, "123")), // authors
                List.of(new GenreDto(1, "123"), new GenreDto(1, "123")) // genres
        );

        when(mangaService.getMangaById(1)).thenReturn(List.of(mangaDto));

        this.mockMvc.perform(get("/api/v1/manga/1")
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Monster"));
    }
}

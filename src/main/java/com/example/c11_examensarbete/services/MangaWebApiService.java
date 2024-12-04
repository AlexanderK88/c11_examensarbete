package com.example.c11_examensarbete.services;
import com.example.c11_examensarbete.dtos.AuthorDto;
import com.example.c11_examensarbete.dtos.GenreDto;
import com.example.c11_examensarbete.dtos.ImageDto;
import com.example.c11_examensarbete.dtos.MangaDto;
import com.example.c11_examensarbete.entities.Author;
import com.example.c11_examensarbete.entities.Genre;
import com.example.c11_examensarbete.entities.Image;
import com.example.c11_examensarbete.entities.Manga;
import com.example.c11_examensarbete.repositories.AuthorRepository;
import com.example.c11_examensarbete.repositories.GenreRepository;
import com.example.c11_examensarbete.repositories.ImageRepository;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;



@Service
public class MangaWebApiService {

    @Autowired
    private MangaRepository mangaRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ImageRepository imageRepository;

    private final RestClient restClient;
    ObjectMapper objectMapper;

    public MangaWebApiService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public String getManga() {
        return restClient
                .get()
                .uri("https://api.jikan.moe/v4/manga")
                .retrieve()
                .body(String.class);
    }


    public void fetchAndSaveMangas() {
        int currentPage = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            try {
                // Fetch manga page data
                String pageData = fetchMangaPage(currentPage);
                JsonNode root = objectMapper.readTree(pageData);

                // Check if there's a next page
                hasNextPage = root.path("pagination").path("has_next_page").asBoolean();

                // Parse and save data
                JsonNode data = root.path("data");
                for (JsonNode mangaNode : data) {
                    MangaDto mangaDto = extractMangaDto(mangaNode);
                    if (mangaDto != null) {
                        saveManga(mangaDto);
                    }
                }
                // Be kind to the API by introducing a delay

                Thread.sleep(3000); // Wait for 3 second between requests
                // Increment the page
                currentPage++;

            } catch (InterruptedException e) {
                // Handle thread interruption gracefully
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
                break;
            } catch (Exception e) {
                // Log and continue
                System.err.println("Error while processing page " + currentPage + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String fetchMangaPage(int page) {
                String pageData = restClient
                        .get()
                        .uri("https://api.jikan.moe/v4/manga?page=" + page)
                        .retrieve()
                        .body(String.class);
        return pageData;
    }

    private MangaDto extractMangaDto(JsonNode node) {
        try {
            int id = node.path("mal_id").asInt();
            String title = node.path("title").asText();
            String titleEnglish = node.path("title_english").asText(null);
            String titleJapanese = node.path("title_japanese").asText(null);
            String type = node.path("type").asText(null);
            Integer volumes = node.path("volumes").isNull() ? null : node.path("volumes").asInt();
            Integer chapters = node.path("chapters").isNull() ? null : node.path("chapters").asInt();
            String status = node.path("status").asText(null);
            String synopsis = node.path("synopsis").asText(null);
            Boolean publishing = node.path("publishing").asBoolean(false);
            Integer popularity = node.path("popularity").asInt(0);
            Integer ranking = node.path("rank").asInt(0);
            BigDecimal score = node.path("score").isNull() ? null : node.path("score").decimalValue();
            Integer scoredBy = node.path("scored_by").isNull() ? null : node.path("scored_by").asInt();

            Instant publishedFrom = parseDate(node.path("published").path("from").asText());
            Instant publishedTo = parseDate(node.path("published").path("to").asText());

            List<ImageDto> images = extractImages(node);
            List<AuthorDto> authors = extractAuthors(node);
            List<GenreDto> genres = extractGenres(node);

            return new MangaDto(
                    id, title, titleEnglish, titleJapanese, type, volumes, chapters, status,
                    synopsis, publishing, popularity, ranking, score, scoredBy, publishedFrom, publishedTo,
                    images, authors, genres
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ImageDto> extractImages(JsonNode node) {
        List<ImageDto> images = new ArrayList<>();
        JsonNode imageNode = node.path("images").path("jpg");
        if (!imageNode.isMissingNode()) {
            images.add(new ImageDto(
                    null,
                    imageNode.path("image_url").asText(null),
                    imageNode.path("large_image_url").asText(null),
                    imageNode.path("small_image_url").asText(null),
                    node.path("mal_id").asInt()));
        }
        return images;
    }

    private List<AuthorDto> extractAuthors(JsonNode node) {
        List<AuthorDto> authors = new ArrayList<>();
        JsonNode authorsNode = node.path("authors");
        for (JsonNode authorNode : authorsNode) {
            authors.add(new AuthorDto(
                    authorNode.path("mal_id").asInt(),
                    authorNode.path("name").asText(null)
            ));
        }
        return authors;
    }

    private List<GenreDto> extractGenres(JsonNode node) {
        List<GenreDto> genres = new ArrayList<>();
        JsonNode genresNode = node.path("genres");
        for (JsonNode genreNode : genresNode) {
            genres.add(new GenreDto(
                    genreNode.path("mal_id").asInt(),
                    genreNode.path("name").asText(null)
            ));
        }
        return genres;
    }

    private Instant parseDate(String dateStr) {
        try {
            return dateStr != null ? Instant.parse(dateStr) : null;
        } catch (Exception e) {
            return null;
        }
    }



@Transactional
    public void saveManga(MangaDto mangaDto) {
        Manga manga = new Manga();
        manga.setId(mangaDto.id());
        manga.setTitle(mangaDto.title());
        manga.setTitleEnglish(mangaDto.titleEnglish());
        manga.setTitleJapanese(mangaDto.titleJapanese());
        manga.setType(mangaDto.type());
        manga.setVolumes(mangaDto.volumes());
        manga.setChapters(mangaDto.chapters());
        manga.setStatus(mangaDto.status());
        manga.setSynopsis(mangaDto.synopsis());
        manga.setPublishing(mangaDto.publishing());
        manga.setPopularity(mangaDto.popularity());
        manga.setRanking(mangaDto.ranking());
        manga.setScore(mangaDto.score());
        manga.setScoredBy(mangaDto.scoredBy());
        manga.setPublishedFrom(mangaDto.publishedFrom());
        manga.setPublishedTo(mangaDto.publishedTo());

        mangaRepository.save(manga);
        for (AuthorDto authorDto : mangaDto.authors()) {
            Author author = authorRepository.findById(authorDto.id())
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setId(authorDto.id());
                        newAuthor.setName(authorDto.name());
                        return authorRepository.save(newAuthor);
                    });
            manga.getAuthors().add(author);
        }

        // Persist genres and add to manga
        for (GenreDto genreDto : mangaDto.genres()) {
            Genre genre = genreRepository.findById(genreDto.id())
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setId(genreDto.id());
                        newGenre.setName(genreDto.name());
                        return genreRepository.save(newGenre); // Persist genre
                    });
            manga.getGenres().add(genre); // Add to relationship
        }

        // Persist images and add to manga
        for (ImageDto imageDto : mangaDto.images()) {
            Image image = new Image();
            image.setImageUrl(imageDto.imageUrl());
            image.setLargeImageUrl(imageDto.largeImageUrl());
            image.setSmallImageUrl(imageDto.smallImageUrl());
            image.setManga(manga); // Set parent
            imageRepository.save(image); // Persist image
            manga.getImages().add(image); // Add to relationship
        }

        // Persist manga

        mangaRepository.save(manga);
    }

}

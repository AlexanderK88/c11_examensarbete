package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "manga")
public class Manga {

    @Id
    private Integer id;

    private String title;

    private String titleEnglish;

    private String titleJapanese;

    private String type;

    private Integer volumes;

    private Integer chapters;

    private String status;

    @Lob
    private String synopsis;

    private Boolean publishing;

    private Integer popularity;

    private Integer ranking;

    private BigDecimal score;

    private Integer scoredBy;

    private Instant publishedFrom;

    private Instant publishedTo;

    // Many-to-Many relationship with Authors
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "manga_authors",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    // Many-to-Many relationship with Genres
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "manga_genres",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    // One-to-Many relationship with Images
    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();
}
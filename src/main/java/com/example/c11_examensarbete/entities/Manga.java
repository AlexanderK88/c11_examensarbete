package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "manga", schema = "mydatabase")
public class Manga {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 255)
    @Column(name = "title_english")
    private String titleEnglish;

    @Size(max = 255)
    @Column(name = "title_japanese")
    private String titleJapanese;

    @Size(max = 255)
    @Column(name = "type")
    private String type;

    @Column(name = "chapters")
    private Integer chapters;

    @Column(name = "volumes")
    private Integer volumes;

    @Size(max = 255)
    @Column(name = "status")
    private String status;

    @Column(name = "publishing")
    private Boolean publishing;

    @Column(name = "published_from")
    private Instant publishedFrom;

    @Column(name = "published_to")
    private Instant publishedTo;

    @Column(name = "score", precision = 3, scale = 2)
    private BigDecimal score;

    @Column(name = "scored_by")
    private Integer scoredBy;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "popularity")
    private Integer popularity;

    @Lob
    @Column(name = "synopsis")
    private String synopsis;

}
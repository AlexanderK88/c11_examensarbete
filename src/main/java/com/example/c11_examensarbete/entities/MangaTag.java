package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "manga_tags", schema = "mydatabase")
public class MangaTag {
    @EmbeddedId
    private MangaTagId id;

    @MapsId("mangaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

}
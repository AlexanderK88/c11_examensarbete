package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "saved_manga", schema = "mydatabase")
public class SavedManga {
    @EmbeddedId
    private SavedMangaId id;

    @MapsId("mangaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;

    @Column(name = "list_id")
    private Integer listId;

}
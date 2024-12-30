package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "list_manga", schema = "mydatabase")
public class ListManga {
    @EmbeddedId
    private ListMangaId id;

    @MapsId("listId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    private List list;

    @MapsId("savedMangaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "saved_manga_id", nullable = false)
    private SavedManga savedManga;

}
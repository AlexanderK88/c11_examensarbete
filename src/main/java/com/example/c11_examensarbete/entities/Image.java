package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl;

    private String smallImageUrl;

    private String largeImageUrl;

    // Many-to-One relationship with Manga
    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;
}

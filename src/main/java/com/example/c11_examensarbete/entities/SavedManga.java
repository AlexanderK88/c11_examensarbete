package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "saved_manga", schema = "mydatabase")
public class SavedManga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @ManyToMany
    @JoinTable(
            name = "list_manga",
            joinColumns = @JoinColumn(name = "saved_manga_id"),
            inverseJoinColumns = @JoinColumn(name = "list_id")
    )
    private Set<List> lists = new LinkedHashSet<>();

    @Column(name = "personal_rating")
    private Float personalRating;

    @ColumnDefault("0")
    @Column(name = "current_chapter")
    private Integer currentChapter;

    @Size(max = 50)
    @ColumnDefault("'not started'")
    @Column(name = "status", length = 50)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    // PrePersist and PreUpdate lifecycle callbacks to set timestamps
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}

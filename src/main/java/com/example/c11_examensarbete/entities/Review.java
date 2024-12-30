package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reviews", schema = "mydatabase")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "review_text")
    private String reviewText;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Column(name = "spoiler")
    private Boolean spoiler;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    private Manga manga;


    @Column(name = "timestamp")
    private Instant timestamp;

    @OneToMany(mappedBy = "review")
    private Set<Comment> comments = new LinkedHashSet<>();

    @PrePersist
    protected void onCreate() {
        timestamp = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        timestamp = Instant.now();
    }
}
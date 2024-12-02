package com.example.c11_examensarbete.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comments", schema = "mydatabase")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "timestamp")
    private Instant timestamp;

}
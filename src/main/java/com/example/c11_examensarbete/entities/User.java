package com.example.c11_examensarbete.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users", schema = "mydatabase")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "email", unique = true)
    private String email;

    @Size(max = 255)
    @Column(name = "role")
    @ColumnDefault("'ROLE_USER'")
    private String role;

    @Size(max = 50)
    @Column(name = "oauth_provider", unique = true)
    private String oauthProvider;

    @Size(max = 255)
    @Column(name = "oauth_provider_id")
    private String oauthProviderId;

    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @Size(max = 255)
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Size(max = 255)
    @Column(name = "access_token")
    private String accessToken;

    @Size(max = 255)
    @Column(name = "refresh_token")
    private String refreshToken;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<List> lists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<SavedManga> savedMangas = new LinkedHashSet<>();

}
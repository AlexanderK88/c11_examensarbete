package com.example.c11_examensarbete.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MangaGenreId implements Serializable {
    private static final long serialVersionUID = -4567002759012331509L;
    @NotNull
    @Column(name = "manga_id", nullable = false)
    private Integer mangaId;

    @NotNull
    @Column(name = "genre_id", nullable = false)
    private Integer genreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MangaGenreId entity = (MangaGenreId) o;
        return Objects.equals(this.genreId, entity.genreId) &&
                Objects.equals(this.mangaId, entity.mangaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, mangaId);
    }

}
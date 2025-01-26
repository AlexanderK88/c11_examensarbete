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
public class MangaAuthorId implements Serializable {
    private static final long serialVersionUID = 2639241313179278134L;
    @NotNull
    @Column(name = "manga_id", nullable = false)
    private Integer mangaId;

    @NotNull
    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MangaAuthorId entity = (MangaAuthorId) o;
        return Objects.equals(this.mangaId, entity.mangaId) &&
                Objects.equals(this.authorId, entity.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mangaId, authorId);
    }

}
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
public class MangaTagId implements Serializable {
    private static final long serialVersionUID = -1708598369347687667L;
    @NotNull
    @Column(name = "manga_id", nullable = false)
    private Integer mangaId;

    @NotNull
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MangaTagId entity = (MangaTagId) o;
        return Objects.equals(this.mangaId, entity.mangaId) &&
                Objects.equals(this.tagId, entity.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mangaId, tagId);
    }

}
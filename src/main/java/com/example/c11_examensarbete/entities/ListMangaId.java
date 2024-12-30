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
public class ListMangaId implements Serializable {
    private static final long serialVersionUID = -4226377004298653721L;
    @NotNull
    @Column(name = "list_id", nullable = false)
    private Integer listId;

    @NotNull
    @Column(name = "saved_manga_id", nullable = false)
    private Integer savedMangaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListMangaId entity = (ListMangaId) o;
        return Objects.equals(this.listId, entity.listId) &&
                Objects.equals(this.savedMangaId, entity.savedMangaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, savedMangaId);
    }

}
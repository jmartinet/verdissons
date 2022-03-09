package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Genre} entity.
 */
public class GenreDTO implements Serializable {

    private Long id;

    private String nom;

    private FamilleDTO famille;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public FamilleDTO getFamille() {
        return famille;
    }

    public void setFamille(FamilleDTO famille) {
        this.famille = famille;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenreDTO)) {
            return false;
        }

        GenreDTO genreDTO = (GenreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenreDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", famille=" + getFamille() +
            "}";
    }
}

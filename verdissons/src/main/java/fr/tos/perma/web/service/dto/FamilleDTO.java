package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Famille} entity.
 */
public class FamilleDTO implements Serializable {

    private Long id;

    private String nom;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilleDTO)) {
            return false;
        }

        FamilleDTO familleDTO = (FamilleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, familleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}

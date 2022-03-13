package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Famille} entity.
 */
public class FamilleDTO extends BotanicItemDTO {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilleDTO)) {
            return false;
        }

        FamilleDTO familleDTO = (FamilleDTO) o;
        if (this.getId() == null) {
            return false;
        }
        return Objects.equals(this.getId(), familleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
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

package fr.tos.perma.web.service.dto;

import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Genre} entity.
 */
public class GenreDTO extends BotanicItemDTO {

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GenreDTO)) {
			return false;
		}

		GenreDTO genreDTO = (GenreDTO) o;
		if (this.getId() == null) {
			return false;
		}
		return Objects.equals(this.getId(), genreDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "GenreDTO{" + "id=" + getId() + ", nom='" + getNom() + "'" + ", famille=" + getParent() + "}";
	}
}

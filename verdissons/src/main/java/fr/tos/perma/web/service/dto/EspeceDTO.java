package fr.tos.perma.web.service.dto;

import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Espece} entity.
 */
public class EspeceDTO extends BotanicItemDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof EspeceDTO)) {
			return false;
		}

		EspeceDTO especeDTO = (EspeceDTO) o;
		if (this.getId() == null) {
			return false;
		}
		return Objects.equals(this.getId(), especeDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "EspeceDTO{" + "id=" + getId() + ", nom='" + getNom() + "'" + ", genre=" + getParent() + "}";
	}
}

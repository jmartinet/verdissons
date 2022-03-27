package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Semence} entity.
 */
public class BoutureDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String typeBouture;

    private String conseilBouture;

    private LocalDate periodeBoutureDebut;

    private LocalDate periodeBoutureFin;

    private CultivarDTO cultivar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getTypeBouture() {
		return typeBouture;
	}

	public void setTypeBouture(String typeBouture) {
		this.typeBouture = typeBouture;
	}

	public String getConseilBouture() {
		return conseilBouture;
	}

	public void setConseilBouture(String conseilBouture) {
		this.conseilBouture = conseilBouture;
	}

	public LocalDate getPeriodeBoutureDebut() {
		return periodeBoutureDebut;
	}

	public void setPeriodeBoutureDebut(LocalDate periodeBoutureDebut) {
		this.periodeBoutureDebut = periodeBoutureDebut;
	}

	public LocalDate getPeriodeBoutureFin() {
		return periodeBoutureFin;
	}

	public void setPeriodeBoutureFin(LocalDate periodeBoutureFin) {
		this.periodeBoutureFin = periodeBoutureFin;
	}

	public CultivarDTO getCultivar() {
		return cultivar;
	}

	public void setCultivar(CultivarDTO cultivar) {
		this.cultivar = cultivar;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoutureDTO)) {
            return false;
        }

        BoutureDTO semenceDTO = (BoutureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, semenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoutureDTO{" +
            "id=" + getId() +
            ", typeBouture='" + getTypeBouture() + "'" +
            ", conseilBouture='" + getConseilBouture() + "'" +
            ", periodeBoutureDebut='" + getPeriodeBoutureDebut() + "'" +
            ", periodeBoutureFin='" + getPeriodeBoutureFin() + "'" +
            ", cultivar=" + getCultivar() +
            "}";
    }
}

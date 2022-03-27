package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Semence} entity.
 */
public class SemenceDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String typeSemis;

    private String conseilSemis;

    private LocalDate periodeSemisDebut;

    private LocalDate periodeSemisFin;

    private CultivarDTO cultivar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeSemis() {
        return typeSemis;
    }

    public void setTypeSemis(String typeSemis) {
        this.typeSemis = typeSemis;
    }

    public String getConseilSemis() {
        return conseilSemis;
    }

    public void setConseilSemis(String conseilSemis) {
        this.conseilSemis = conseilSemis;
    }

    public LocalDate getPeriodeSemisDebut() {
        return periodeSemisDebut;
    }

    public void setPeriodeSemisDebut(LocalDate periodeSemisDebut) {
        this.periodeSemisDebut = periodeSemisDebut;
    }

    public LocalDate getPeriodeSemisFin() {
        return periodeSemisFin;
    }

    public void setPeriodeSemisFin(LocalDate periodeSemisFin) {
        this.periodeSemisFin = periodeSemisFin;
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
        if (!(o instanceof SemenceDTO)) {
            return false;
        }

        SemenceDTO semenceDTO = (SemenceDTO) o;
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
        return "SemenceDTO{" +
            "id=" + getId() +
            ", typeSemis='" + getTypeSemis() + "'" +
            ", conseilSemis='" + getConseilSemis() + "'" +
            ", periodeSemisDebut='" + getPeriodeSemisDebut() + "'" +
            ", periodeSemisFin='" + getPeriodeSemisFin() + "'" +
            ", cultivar=" + getCultivar() +
            "}";
    }
}

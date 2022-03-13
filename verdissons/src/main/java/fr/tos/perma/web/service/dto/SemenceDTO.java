package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Semence} entity.
 */
public class SemenceDTO implements Serializable {

    private Long id;

    private String typeSemis;

    private String conseilSemis;

    private LocalDate periodeSemisDebut;

    private LocalDate periodeSemisFin;

    private VarieteDTO variete;

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

    public VarieteDTO getVariete() {
        return variete;
    }

    public void setVariete(VarieteDTO variete) {
        this.variete = variete;
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
            ", variete=" + getVariete() +
            "}";
    }
}

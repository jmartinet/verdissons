package fr.tos.perma.web.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tos.perma.web.domain.Variete} entity.
 */
public class VarieteDTO implements Serializable {

    private Long id;

    private String nom;

    private String conseilCulture;

    private String culture;

    private String exposition;

    private String besoinEau;

    private String natureSol;

    private String qualiteSol;

    private EspeceDTO espece;

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

    public String getConseilCulture() {
        return conseilCulture;
    }

    public void setConseilCulture(String conseilCulture) {
        this.conseilCulture = conseilCulture;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getExposition() {
        return exposition;
    }

    public void setExposition(String exposition) {
        this.exposition = exposition;
    }

    public String getBesoinEau() {
        return besoinEau;
    }

    public void setBesoinEau(String besoinEau) {
        this.besoinEau = besoinEau;
    }

    public String getNatureSol() {
        return natureSol;
    }

    public void setNatureSol(String natureSol) {
        this.natureSol = natureSol;
    }

    public String getQualiteSol() {
        return qualiteSol;
    }

    public void setQualiteSol(String qualiteSol) {
        this.qualiteSol = qualiteSol;
    }

    public EspeceDTO getEspece() {
        return espece;
    }

	public void setEspece(EspeceDTO genre) {
        this.espece = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VarieteDTO)) {
            return false;
        }

        VarieteDTO varieteDTO = (VarieteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, varieteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VarieteDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", conseilCulture='" + getConseilCulture() + "'" +
            ", culture='" + getCulture() + "'" +
            ", exposition='" + getExposition() + "'" +
            ", besoinEau='" + getBesoinEau() + "'" +
            ", natureSol='" + getNatureSol() + "'" +
            ", qualiteSol='" + getQualiteSol() + "'" +
            ", genre=" + getEspece() +
            "}";
    }
}

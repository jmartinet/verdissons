package fr.tos.perma.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Semence.
 */
@Entity
@Table(name = "semence")
public class Semence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type_semis")
    private String typeSemis;

    @Column(name = "conseil_semis")
    private String conseilSemis;

    @Column(name = "periode_semis_debut")
    private LocalDate periodeSemisDebut;

    @Column(name = "periode_semis_fin")
    private LocalDate periodeSemisFin;

    @ManyToOne
    @JsonIgnoreProperties(value = { "genre" }, allowSetters = true)
    private Variete variete;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Semence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeSemis() {
        return this.typeSemis;
    }

    public Semence typeSemis(String typeSemis) {
        this.setTypeSemis(typeSemis);
        return this;
    }

    public void setTypeSemis(String typeSemis) {
        this.typeSemis = typeSemis;
    }

    public String getConseilSemis() {
        return this.conseilSemis;
    }

    public Semence conseilSemis(String conseilSemis) {
        this.setConseilSemis(conseilSemis);
        return this;
    }

    public void setConseilSemis(String conseilSemis) {
        this.conseilSemis = conseilSemis;
    }

    public LocalDate getPeriodeSemisDebut() {
        return this.periodeSemisDebut;
    }

    public Semence periodeSemisDebut(LocalDate periodeSemisDebut) {
        this.setPeriodeSemisDebut(periodeSemisDebut);
        return this;
    }

    public void setPeriodeSemisDebut(LocalDate periodeSemisDebut) {
        this.periodeSemisDebut = periodeSemisDebut;
    }

    public LocalDate getPeriodeSemisFin() {
        return this.periodeSemisFin;
    }

    public Semence periodeSemisFin(LocalDate periodeSemisFin) {
        this.setPeriodeSemisFin(periodeSemisFin);
        return this;
    }

    public void setPeriodeSemisFin(LocalDate periodeSemisFin) {
        this.periodeSemisFin = periodeSemisFin;
    }

    public Variete getVariete() {
        return this.variete;
    }

    public void setVariete(Variete variete) {
        this.variete = variete;
    }

    public Semence variete(Variete variete) {
        this.setVariete(variete);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Semence)) {
            return false;
        }
        return id != null && id.equals(((Semence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Semence{" +
            "id=" + getId() +
            ", typeSemis='" + getTypeSemis() + "'" +
            ", conseilSemis='" + getConseilSemis() + "'" +
            ", periodeSemisDebut='" + getPeriodeSemisDebut() + "'" +
            ", periodeSemisFin='" + getPeriodeSemisFin() + "'" +
            "}";
    }
}

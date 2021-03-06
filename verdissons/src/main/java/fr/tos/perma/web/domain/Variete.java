package fr.tos.perma.web.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A Variete.
 */
@Entity
@Table(name = "variete")
public class Variete implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "image")
	private String image;

	@Column(name = "conseil_culture")
	private String conseilCulture;

	@Column(name = "culture")
	private String culture;

	@Column(name = "exposition")
	private String exposition;

	@Column(name = "besoin_eau")
	private String besoinEau;

	@Column(name = "nature_sol")
	private String natureSol;

	@Column(name = "qualite_sol")
	private String qualiteSol;

	@ManyToOne
	private Espece espece;

	@OneToMany(mappedBy = "variete")
	private List<Cultivar> cultivars;

	// jhipster-needle-entity-add-field - JHipster will add fields here

	public Long getId() {
		return this.id;
	}

	public Variete id(Long id) {
		this.setId(id);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public Variete nom(String nom) {
		this.setNom(nom);
		return this;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getConseilCulture() {
		return this.conseilCulture;
	}

	public Variete conseilCulture(String conseilCulture) {
		this.setConseilCulture(conseilCulture);
		return this;
	}

	public void setConseilCulture(String conseilCulture) {
		this.conseilCulture = conseilCulture;
	}

	public String getCulture() {
		return this.culture;
	}

	public Variete culture(String culture) {
		this.setCulture(culture);
		return this;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getExposition() {
		return this.exposition;
	}

	public Variete exposition(String exposition) {
		this.setExposition(exposition);
		return this;
	}

	public void setExposition(String exposition) {
		this.exposition = exposition;
	}

	public String getBesoinEau() {
		return this.besoinEau;
	}

	public Variete besoinEau(String besoinEau) {
		this.setBesoinEau(besoinEau);
		return this;
	}

	public void setBesoinEau(String besoinEau) {
		this.besoinEau = besoinEau;
	}

	public String getNatureSol() {
		return this.natureSol;
	}

	public Variete natureSol(String natureSol) {
		this.setNatureSol(natureSol);
		return this;
	}

	public void setNatureSol(String natureSol) {
		this.natureSol = natureSol;
	}

	public String getQualiteSol() {
		return this.qualiteSol;
	}

	public Variete qualiteSol(String qualiteSol) {
		this.setQualiteSol(qualiteSol);
		return this;
	}

	public void setQualiteSol(String qualiteSol) {
		this.qualiteSol = qualiteSol;
	}

	public Espece getEspece() {
		return this.espece;
	}

	public void setEspece(Espece genre) {
		this.espece = genre;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Variete)) {
			return false;
		}
		return id != null && id.equals(((Variete) o).id);
	}

	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Variete{" + "id=" + getId() + ", nom='" + getNom() + "'" + ", conseilCulture='"
				+ getConseilCulture() + "'" + ", culture='" + getCulture() + "'" + ", exposition='" + getExposition()
				+ "'" + ", besoinEau='" + getBesoinEau() + "'" + ", natureSol='" + getNatureSol() + "'"
				+ ", qualiteSol='" + getQualiteSol() + "'" + "}";
	}

	public List<Cultivar> getCultivars() {
		return cultivars;
	}

	public void setCultivars(List<Cultivar> cultivars) {
		this.cultivars = cultivars;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}

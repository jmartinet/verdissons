package fr.tos.perma.web.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "periode_reproduction")
public class PeriodeReproduction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TypeReproduction type;

	@Column(name = "conseil")
	private String conseil;

	@Column(name = "periode_debut")
	private LocalDate periodeDebut;

	@Column(name = "periode_fin")
	private LocalDate periodeFin;

	@ManyToOne
	private Cultivar cultivar;

	@ManyToOne
	private Variete variete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeReproduction getType() {
		return type;
	}

	public void setType(TypeReproduction type) {
		this.type = type;
	}

	public String getConseil() {
		return conseil;
	}

	public void setConseil(String conseil) {
		this.conseil = conseil;
	}

	public LocalDate getPeriodeDebut() {
		return periodeDebut;
	}

	public void setPeriodeDebut(LocalDate periodeDebut) {
		this.periodeDebut = periodeDebut;
	}

	public LocalDate getPeriodeFin() {
		return periodeFin;
	}

	public void setPeriodeFin(LocalDate periodeFin) {
		this.periodeFin = periodeFin;
	}

	public Cultivar getCultivar() {
		return cultivar;
	}

	public void setCultivar(Cultivar cultivar) {
		this.cultivar = cultivar;
	}

	public Variete getVariete() {
		return variete;
	}

	public void setVariete(Variete variete) {
		this.variete = variete;
	}

}

package fr.tos.perma.web.service.dto;

public class CultivarDTO {

	private Long id;

	private VarieteDTO variete;

	private String nom;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VarieteDTO getVariete() {
		return variete;
	}

	public void setVariete(VarieteDTO variete) {
		this.variete = variete;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

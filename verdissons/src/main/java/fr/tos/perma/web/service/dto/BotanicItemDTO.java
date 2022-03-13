package fr.tos.perma.web.service.dto;

import java.io.Serializable;

public class BotanicItemDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String nom;

    private BotanicItemDTO parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public BotanicItemDTO getParent() {
		return parent;
	}

	public void setParent(BotanicItemDTO parent) {
		this.parent = parent;
	}

}

package fr.tos.perma.web.service.dto;

import java.io.Serializable;

import fr.tos.perma.web.domain.TypeBotanicItem;

public class BotanicItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nom;

	private TypeBotanicItem type;

	private BotanicItemDTO parent;

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

	public BotanicItemDTO getParent() {
		return parent;
	}

	public void setParent(BotanicItemDTO parent) {
		this.parent = parent;
	}

	public TypeBotanicItem getType() {
		return type;
	}

	public void setType(TypeBotanicItem type) {
		this.type = type;
	}

}

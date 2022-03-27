package fr.tos.perma.web.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "botanic_items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "botanic_type", discriminatorType = DiscriminatorType.STRING)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "libelle", "botanic_type" }) })
public class BotanicItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "libelle")
	private String libelle;

	@Column(name = "botanic_type", insertable=false, updatable=false)
	private String type;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private BotanicItem parent;

	@OneToMany(mappedBy = "parent")
	private List<BotanicItem> children;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public BotanicItem getParent() {
		return parent;
	}

	public void setParent(BotanicItem parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

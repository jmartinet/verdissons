package fr.tos.perma.web.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
public class BotanicItemCriteria implements Serializable, Criteria {

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private LongFilter parent;

	private StringFilter libelle;

	private StringFilter type;

	private Boolean distinct;

	private Boolean emptyValues;

	public BotanicItemCriteria() {
	}

	public BotanicItemCriteria(BotanicItemCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.parent = other.parent == null ? null : other.parent.copy();
		this.distinct = other.distinct;
		this.emptyValues = other.emptyValues;
	}

	@Override
	public BotanicItemCriteria copy() {
		return new BotanicItemCriteria(this);
	}

	public LongFilter getId() {
		return id;
	}

	public LongFilter id() {
		if (id == null) {
			id = new LongFilter();
		}
		return id;
	}

	public void setId(LongFilter id) {
		this.id = id;
	}

	public StringFilter getLibelle() {
		return libelle;
	}

	public StringFilter libelle() {
		if (libelle == null) {
			libelle = new StringFilter();
		}
		return libelle;
	}

	public void setLibelle(StringFilter libelle) {
		this.libelle = libelle;
	}

	public StringFilter getType() {
		return type;
	}

	public StringFilter type() {
		if (type == null) {
			type = new StringFilter();
		}
		return type;
	}

	public void setType(StringFilter type) {
		this.type = type;
	}

	public LongFilter getParent() {
		return parent;
	}

	public LongFilter parent() {
		if (parent == null) {
			parent = new LongFilter();
		}
		return parent;
	}

	public void setParent(LongFilter parent) {
		this.parent = parent;
	}

	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	public Boolean getEmptyValues() {
		return emptyValues;
	}

	public void setEmptyValues(Boolean emptyValues) {
		this.emptyValues = emptyValues;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final BotanicItemCriteria that = (BotanicItemCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(libelle, that.libelle) && Objects.equals(type, that.type)
				&& Objects.equals(distinct, that.distinct) && Objects.equals(emptyValues, that.emptyValues);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, libelle, distinct, emptyValues);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "BotanicItemCriteria{" 
				+ (id != null ? "id=" + id + ", " : "")
				+ (libelle != null ? "libelle=" + libelle + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (emptyValues != null ? "distinct=" + emptyValues + ", " : "")
				+ (distinct != null ? "distinct=" + distinct + ", " : "") + "}";
	}

}

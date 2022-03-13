package fr.tos.perma.web.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;

/**
 * Criteria class for the {@link fr.tos.perma.web.domain.Genre} entity. This
 * class is used in {@link fr.tos.perma.web.web.rest.GenreResource} to receive
 * all the possible filtering options from the Http GET request parameters. For
 * example the following could be a valid request:
 * {@code /especes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@ParameterObject
public class GenreCriteria implements Serializable, Criteria {

	private static final long serialVersionUID = 1L;

	private IntegerFilter id;

	private Boolean distinct;

	public GenreCriteria() {
	}

	public GenreCriteria(GenreCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.distinct = other.distinct;
	}

	@Override
	public GenreCriteria copy() {
		return new GenreCriteria(this);
	}

	public IntegerFilter getId() {
		return id;
	}

	public IntegerFilter id() {
		if (id == null) {
			id = new IntegerFilter();
		}
		return id;
	}

	public void setId(IntegerFilter id) {
		this.id = id;
	}

	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final GenreCriteria that = (GenreCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(distinct, that.distinct);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, distinct);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "GenreCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (distinct != null ? "distinct=" + distinct + ", " : "") + "}";
	}
}
package fr.tos.perma.web.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.domain.BotanicItem;
import fr.tos.perma.web.domain.BotanicItem_;
import fr.tos.perma.web.repository.BotanicItemRepository;
import fr.tos.perma.web.service.criteria.BotanicItemCriteria;
import fr.tos.perma.web.service.criteria.EspeceCriteria;
import fr.tos.perma.web.service.dto.BotanicItemDTO;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class BotanicItemQueryService extends QueryService<BotanicItem> {

	private final Logger log = LoggerFactory.getLogger(BotanicItemQueryService.class);

	private final BotanicItemRepository botanicItemRepository;

	private final BotanicItemMapper botanicMapper;

	public BotanicItemQueryService(BotanicItemRepository botanicItemRepository) {
		this.botanicItemRepository = botanicItemRepository;
		this.botanicMapper = new BotanicItemMapper();
	}

	/**
	 * Return a {@link List} of {@link EspeceDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<BotanicItemDTO> findByCriteria(BotanicItemCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}", criteria);
		final Specification<BotanicItem> specification = createSpecification(criteria);
		Page<BotanicItem> items = botanicItemRepository.findAll(specification, page);
		Page<BotanicItemDTO> itemDTOs = items.map(botanicMapper::toDto);
		return itemDTOs;
	}

	/**
	 * Function to convert {@link EspeceCriteria} to a {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<BotanicItem> createSpecification(BotanicItemCriteria criteria) {
		Specification<BotanicItem> specification = Specification.where(null);
		if (criteria != null) {
			// This has to be called first, because the distinct method returns null
			if (criteria.getEmptyValues() != null && !criteria.getEmptyValues().booleanValue()) {
				specification = specification.and(emptyItemFilter());
			}
			if (criteria.getDistinct() != null) {
				specification = specification.and(distinct(criteria.getDistinct()));
			}
			if (criteria.getId() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), BotanicItem_.id));
			}
			if (criteria.getLibelle() != null) {
				specification = specification.and(libelleFilter(criteria.getLibelle().getContains()));
			}
			if (criteria.getParent() != null) {
				specification = specification.and(
						buildReferringEntitySpecification(criteria.getParent(), BotanicItem_.parent, BotanicItem_.id));
			}
			if (criteria.getType() != null) {
				specification = specification.and(buildStringSpecification(criteria.getType(), BotanicItem_.type));
			}
		}
		return specification;
	}

	private static Specification<BotanicItem> libelleFilter(String libelle) {
		return new Specification<BotanicItem>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<BotanicItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {				
				Join<BotanicItem, BotanicItem> joinGenre = root.join(BotanicItem_.parent);
				
				Join<BotanicItem, BotanicItem> joinFamille = joinGenre.join(BotanicItem_.parent);
				return cb.or(cb.like(root.get(BotanicItem_.libelle), "%" + libelle + "%"),
						cb.or(cb.like(joinGenre.get(BotanicItem_.libelle), "%" + libelle + "%"),
								cb.like(joinFamille.get(BotanicItem_.libelle), "%" + libelle + "%")));
			}

		};
	}

	private static Specification<BotanicItem> emptyItemFilter() {
		return new Specification<BotanicItem>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<BotanicItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Subquery<BotanicItem> childrenQuery = query.subquery(BotanicItem.class);
				Root<BotanicItem> child = childrenQuery.from(BotanicItem.class);
				childrenQuery.select(child).where(cb.equal(child.get(BotanicItem_.parent), root));
				return cb.or(cb.equal(root.get(BotanicItem_.type), "ESPECE"),
						cb.and(cb.notEqual(root.get(BotanicItem_.type), "ESPECE"), cb.exists(childrenQuery)));
			}

		};
	}

}

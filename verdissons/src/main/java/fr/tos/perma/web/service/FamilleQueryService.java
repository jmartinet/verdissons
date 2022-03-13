package fr.tos.perma.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.domain.BotanicItem_;
// for static metamodels
import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.repository.FamilleRepository;
import fr.tos.perma.web.service.criteria.FamilleCriteria;
import fr.tos.perma.web.service.dto.FamilleDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Famille} entities in the
 * database. The main input is a {@link FamilleCriteria} which gets converted to
 * {@link Specification}, in a way that all the filters must apply. It returns a
 * {@link List} of {@link FamilleDTO} or a {@link Page} of {@link FamilleDTO}
 * which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FamilleQueryService extends QueryService<Famille> {

	private final Logger log = LoggerFactory.getLogger(FamilleQueryService.class);

	private final FamilleRepository familleRepository;

	private final BotanicItemMapper<Famille, FamilleDTO> familleMapper;

	public FamilleQueryService(FamilleRepository especeRepository) {
		this.familleRepository = especeRepository;
		this.familleMapper = new BotanicItemMapper<Famille, FamilleDTO>();
	}

	/**
	 * Return a {@link List} of {@link FamilleDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<FamilleDTO> findByCriteria(FamilleCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<Famille> specification = createSpecification(criteria);
		return familleRepository.findAll(specification).stream().map(familleMapper::toDto).toList();
	}

	/**
	 * Return a {@link Page} of {@link FamilleDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<FamilleDTO> findByCriteria(FamilleCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<Famille> specification = createSpecification(criteria);
		return familleRepository.findAll(specification, page).map(familleMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(FamilleCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<Famille> specification = createSpecification(criteria);
		return familleRepository.count(specification);
	}

	/**
	 * Function to convert {@link FamilleCriteria} to a {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<Famille> createSpecification(FamilleCriteria criteria) {
		Specification<Famille> specification = Specification.where(null);
		if (criteria != null) {
			// This has to be called first, because the distinct method returns null
			if (criteria.getDistinct() != null) {
				specification = specification.and(distinct(criteria.getDistinct()));
			}
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BotanicItem_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), BotanicItem_.libelle));
            }
		}
		return specification;
	}
}

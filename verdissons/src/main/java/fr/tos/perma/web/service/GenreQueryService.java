package fr.tos.perma.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import fr.tos.perma.web.domain.Genre;
import fr.tos.perma.web.repository.GenreRepository;
import fr.tos.perma.web.service.criteria.GenreCriteria;
import fr.tos.perma.web.service.dto.GenreDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Genre} entities in the
 * database. The main input is a {@link GenreCriteria} which gets converted to
 * {@link Specification}, in a way that all the filters must apply. It returns a
 * {@link List} of {@link GenreDTO} or a {@link Page} of {@link GenreDTO} which
 * fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GenreQueryService extends QueryService<Genre> {

	private final Logger log = LoggerFactory.getLogger(GenreQueryService.class);

	private final GenreRepository especeRepository;

	private final BotanicItemMapper<Genre, GenreDTO> especeMapper;

	public GenreQueryService(GenreRepository especeRepository) {
		this.especeRepository = especeRepository;
		this.especeMapper = new BotanicItemMapper<Genre, GenreDTO>();
	}

	/**
	 * Return a {@link List} of {@link GenreDTO} which matches the criteria from the
	 * database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<GenreDTO> findByCriteria(GenreCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<Genre> specification = createSpecification(criteria);
		return especeRepository.findAll(specification).stream().map(especeMapper::toDto).toList();
	}

	/**
	 * Return a {@link Page} of {@link GenreDTO} which matches the criteria from the
	 * database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<GenreDTO> findByCriteria(GenreCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<Genre> specification = createSpecification(criteria);
		return especeRepository.findAll(specification, page).map(especeMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(GenreCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<Genre> specification = createSpecification(criteria);
		return especeRepository.count(specification);
	}

	/**
	 * Function to convert {@link GenreCriteria} to a {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<Genre> createSpecification(GenreCriteria criteria) {
		Specification<Genre> specification = Specification.where(null);
		if (criteria != null) {
			// This has to be called first, because the distinct method returns null
			if (criteria.getDistinct() != null) {
				specification = specification.and(distinct(criteria.getDistinct()));
			}
//            if (criteria.getId() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getId(), BotanicItem_.id));
//            }
		}
		return specification;
	}
}

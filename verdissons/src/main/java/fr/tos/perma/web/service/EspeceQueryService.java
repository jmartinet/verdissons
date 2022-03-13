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
import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.repository.EspeceRepository;
import fr.tos.perma.web.service.criteria.EspeceCriteria;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Espece} entities in the database.
 * The main input is a {@link EspeceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspeceDTO} or a {@link Page} of {@link EspeceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspeceQueryService extends QueryService<Espece> {

    private final Logger log = LoggerFactory.getLogger(EspeceQueryService.class);

    private final EspeceRepository especeRepository;

    private final BotanicItemMapper<Espece, EspeceDTO> especeMapper;

    public EspeceQueryService(EspeceRepository especeRepository) {
        this.especeRepository = especeRepository;
        this.especeMapper = new BotanicItemMapper<Espece, EspeceDTO>();
    }

    /**
     * Return a {@link List} of {@link EspeceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspeceDTO> findByCriteria(EspeceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Espece> specification = createSpecification(criteria);
        return especeRepository.findAll(specification).stream().map(especeMapper::toDto).toList();
    }

    /**
     * Return a {@link Page} of {@link EspeceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspeceDTO> findByCriteria(EspeceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Espece> specification = createSpecification(criteria);
        return especeRepository.findAll(specification, page).map(especeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspeceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Espece> specification = createSpecification(criteria);
        return especeRepository.count(specification);
    }

    /**
     * Function to convert {@link EspeceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Espece> createSpecification(EspeceCriteria criteria) {
        Specification<Espece> specification = Specification.where(null);
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

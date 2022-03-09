package fr.tos.perma.web.service;

import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.repository.VarieteRepository;
import fr.tos.perma.web.service.dto.VarieteDTO;
import fr.tos.perma.web.service.mapper.VarieteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Variete}.
 */
@Service
@Transactional
public class VarieteService {

    private final Logger log = LoggerFactory.getLogger(VarieteService.class);

    private final VarieteRepository varieteRepository;

    private final VarieteMapper varieteMapper;

    public VarieteService(VarieteRepository varieteRepository, VarieteMapper varieteMapper) {
        this.varieteRepository = varieteRepository;
        this.varieteMapper = varieteMapper;
    }

    /**
     * Save a variete.
     *
     * @param varieteDTO the entity to save.
     * @return the persisted entity.
     */
    public VarieteDTO save(VarieteDTO varieteDTO) {
        log.debug("Request to save Variete : {}", varieteDTO);
        Variete variete = varieteMapper.toEntity(varieteDTO);
        variete = varieteRepository.save(variete);
        return varieteMapper.toDto(variete);
    }

    /**
     * Partially update a variete.
     *
     * @param varieteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VarieteDTO> partialUpdate(VarieteDTO varieteDTO) {
        log.debug("Request to partially update Variete : {}", varieteDTO);

        return varieteRepository
            .findById(varieteDTO.getId())
            .map(existingVariete -> {
                varieteMapper.partialUpdate(existingVariete, varieteDTO);

                return existingVariete;
            })
            .map(varieteRepository::save)
            .map(varieteMapper::toDto);
    }

    /**
     * Get all the varietes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VarieteDTO> findAll() {
        log.debug("Request to get all Varietes");
        return varieteRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(varieteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the varietes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VarieteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return varieteRepository.findAllWithEagerRelationships(pageable).map(varieteMapper::toDto);
    }

    /**
     * Get one variete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VarieteDTO> findOne(Long id) {
        log.debug("Request to get Variete : {}", id);
        return varieteRepository.findOneWithEagerRelationships(id).map(varieteMapper::toDto);
    }

    /**
     * Delete the variete by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Variete : {}", id);
        varieteRepository.deleteById(id);
    }
}

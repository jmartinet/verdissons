package fr.tos.perma.web.service;

import fr.tos.perma.web.domain.Semence;
import fr.tos.perma.web.repository.SemenceRepository;
import fr.tos.perma.web.service.dto.SemenceDTO;
import fr.tos.perma.web.service.mapper.SemenceMapper;
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
 * Service Implementation for managing {@link Semence}.
 */
@Service
@Transactional
public class SemenceService {

    private final Logger log = LoggerFactory.getLogger(SemenceService.class);

    private final SemenceRepository semenceRepository;

    private final SemenceMapper semenceMapper;

    public SemenceService(SemenceRepository semenceRepository, SemenceMapper semenceMapper) {
        this.semenceRepository = semenceRepository;
        this.semenceMapper = semenceMapper;
    }

    /**
     * Save a semence.
     *
     * @param semenceDTO the entity to save.
     * @return the persisted entity.
     */
    public SemenceDTO save(SemenceDTO semenceDTO) {
        log.debug("Request to save Semence : {}", semenceDTO);
        Semence semence = semenceMapper.toEntity(semenceDTO);
        semence = semenceRepository.save(semence);
        return semenceMapper.toDto(semence);
    }

    /**
     * Partially update a semence.
     *
     * @param semenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SemenceDTO> partialUpdate(SemenceDTO semenceDTO) {
        log.debug("Request to partially update Semence : {}", semenceDTO);

        return semenceRepository
            .findById(semenceDTO.getId())
            .map(existingSemence -> {
                semenceMapper.partialUpdate(existingSemence, semenceDTO);

                return existingSemence;
            })
            .map(semenceRepository::save)
            .map(semenceMapper::toDto);
    }

    /**
     * Get all the semences.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SemenceDTO> findAll() {
        log.debug("Request to get all Semences");
        return semenceRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(semenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the semences with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SemenceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return semenceRepository.findAllWithEagerRelationships(pageable).map(semenceMapper::toDto);
    }

    /**
     * Get one semence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SemenceDTO> findOne(Long id) {
        log.debug("Request to get Semence : {}", id);
        return semenceRepository.findOneWithEagerRelationships(id).map(semenceMapper::toDto);
    }

    /**
     * Delete the semence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Semence : {}", id);
        semenceRepository.deleteById(id);
    }
}

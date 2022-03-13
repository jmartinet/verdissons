package fr.tos.perma.web.web.rest;

import fr.tos.perma.web.repository.SemenceRepository;
import fr.tos.perma.web.service.SemenceService;
import fr.tos.perma.web.service.dto.SemenceDTO;
import fr.tos.perma.web.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.tos.perma.web.domain.Semence}.
 */
@RestController
@RequestMapping("/api")
public class SemenceResource {

    private final Logger log = LoggerFactory.getLogger(SemenceResource.class);

    private static final String ENTITY_NAME = "semence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SemenceService semenceService;

    private final SemenceRepository semenceRepository;

    public SemenceResource(SemenceService semenceService, SemenceRepository semenceRepository) {
        this.semenceService = semenceService;
        this.semenceRepository = semenceRepository;
    }

    /**
     * {@code POST  /semences} : Create a new semence.
     *
     * @param semenceDTO the semenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new semenceDTO, or with status {@code 400 (Bad Request)} if the semence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/semences")
    public ResponseEntity<SemenceDTO> createSemence(@RequestBody SemenceDTO semenceDTO) throws URISyntaxException {
        log.debug("REST request to save Semence : {}", semenceDTO);
        if (semenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new semence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SemenceDTO result = semenceService.save(semenceDTO);
        return ResponseEntity
            .created(new URI("/api/semences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /semences/:id} : Updates an existing semence.
     *
     * @param id the id of the semenceDTO to save.
     * @param semenceDTO the semenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semenceDTO,
     * or with status {@code 400 (Bad Request)} if the semenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the semenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/semences/{id}")
    public ResponseEntity<SemenceDTO> updateSemence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SemenceDTO semenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Semence : {}, {}", id, semenceDTO);
        if (semenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, semenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!semenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SemenceDTO result = semenceService.save(semenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, semenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /semences/:id} : Partial updates given fields of an existing semence, field will ignore if it is null
     *
     * @param id the id of the semenceDTO to save.
     * @param semenceDTO the semenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semenceDTO,
     * or with status {@code 400 (Bad Request)} if the semenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the semenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the semenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/semences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SemenceDTO> partialUpdateSemence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SemenceDTO semenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Semence partially : {}, {}", id, semenceDTO);
        if (semenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, semenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!semenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SemenceDTO> result = semenceService.partialUpdate(semenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, semenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /semences} : get all the semences.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of semences in body.
     */
    @GetMapping("/semences")
    public List<SemenceDTO> getAllSemences(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Semences");
        return semenceService.findAll();
    }

    /**
     * {@code GET  /semences/:id} : get the "id" semence.
     *
     * @param id the id of the semenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the semenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/semences/{id}")
    public ResponseEntity<SemenceDTO> getSemence(@PathVariable Long id) {
        log.debug("REST request to get Semence : {}", id);
        Optional<SemenceDTO> semenceDTO = semenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(semenceDTO);
    }

    /**
     * {@code DELETE  /semences/:id} : delete the "id" semence.
     *
     * @param id the id of the semenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/semences/{id}")
    public ResponseEntity<Void> deleteSemence(@PathVariable Long id) {
        log.debug("REST request to delete Semence : {}", id);
        semenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

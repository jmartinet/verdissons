package fr.tos.perma.web.web.rest;

import fr.tos.perma.web.repository.FamilleRepository;
import fr.tos.perma.web.service.FamilleService;
import fr.tos.perma.web.service.dto.FamilleDTO;
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
 * REST controller for managing {@link fr.tos.perma.web.domain.Famille}.
 */
@RestController
@RequestMapping("/api")
public class FamilleResource {

    private final Logger log = LoggerFactory.getLogger(FamilleResource.class);

    private static final String ENTITY_NAME = "famille";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilleService familleService;

    private final FamilleRepository familleRepository;

    public FamilleResource(FamilleService familleService, FamilleRepository familleRepository) {
        this.familleService = familleService;
        this.familleRepository = familleRepository;
    }

    /**
     * {@code POST  /familles} : Create a new famille.
     *
     * @param familleDTO the familleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familleDTO, or with status {@code 400 (Bad Request)} if the famille has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/familles")
    public ResponseEntity<FamilleDTO> createFamille(@RequestBody FamilleDTO familleDTO) throws URISyntaxException {
        log.debug("REST request to save Famille : {}", familleDTO);
        if (familleDTO.getId() != null) {
            throw new BadRequestAlertException("A new famille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilleDTO result = familleService.save(familleDTO);
        return ResponseEntity
            .created(new URI("/api/familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /familles/:id} : Updates an existing famille.
     *
     * @param id the id of the familleDTO to save.
     * @param familleDTO the familleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familleDTO,
     * or with status {@code 400 (Bad Request)} if the familleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/familles/{id}")
    public ResponseEntity<FamilleDTO> updateFamille(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FamilleDTO familleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Famille : {}, {}", id, familleDTO);
        if (familleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FamilleDTO result = familleService.save(familleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, familleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /familles/:id} : Partial updates given fields of an existing famille, field will ignore if it is null
     *
     * @param id the id of the familleDTO to save.
     * @param familleDTO the familleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familleDTO,
     * or with status {@code 400 (Bad Request)} if the familleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the familleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the familleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/familles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FamilleDTO> partialUpdateFamille(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FamilleDTO familleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Famille partially : {}, {}", id, familleDTO);
        if (familleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FamilleDTO> result = familleService.partialUpdate(familleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, familleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /familles} : get all the familles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familles in body.
     */
    @GetMapping("/familles")
    public List<FamilleDTO> getAllFamilles() {
        log.debug("REST request to get all Familles");
        return familleService.findAll();
    }

    /**
     * {@code GET  /familles/:id} : get the "id" famille.
     *
     * @param id the id of the familleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/familles/{id}")
    public ResponseEntity<FamilleDTO> getFamille(@PathVariable Long id) {
        log.debug("REST request to get Famille : {}", id);
        Optional<FamilleDTO> familleDTO = familleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familleDTO);
    }

    /**
     * {@code DELETE  /familles/:id} : delete the "id" famille.
     *
     * @param id the id of the familleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/familles/{id}")
    public ResponseEntity<Void> deleteFamille(@PathVariable Long id) {
        log.debug("REST request to delete Famille : {}", id);
        familleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

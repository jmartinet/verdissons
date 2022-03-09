package fr.tos.perma.web.web.rest;

import fr.tos.perma.web.repository.VarieteRepository;
import fr.tos.perma.web.service.VarieteService;
import fr.tos.perma.web.service.dto.VarieteDTO;
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
 * REST controller for managing {@link fr.tos.perma.web.domain.Variete}.
 */
@RestController
@RequestMapping("/api")
public class VarieteResource {

    private final Logger log = LoggerFactory.getLogger(VarieteResource.class);

    private static final String ENTITY_NAME = "variete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VarieteService varieteService;

    private final VarieteRepository varieteRepository;

    public VarieteResource(VarieteService varieteService, VarieteRepository varieteRepository) {
        this.varieteService = varieteService;
        this.varieteRepository = varieteRepository;
    }

    /**
     * {@code POST  /varietes} : Create a new variete.
     *
     * @param varieteDTO the varieteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new varieteDTO, or with status {@code 400 (Bad Request)} if the variete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/varietes")
    public ResponseEntity<VarieteDTO> createVariete(@RequestBody VarieteDTO varieteDTO) throws URISyntaxException {
        log.debug("REST request to save Variete : {}", varieteDTO);
        if (varieteDTO.getId() != null) {
            throw new BadRequestAlertException("A new variete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VarieteDTO result = varieteService.save(varieteDTO);
        return ResponseEntity
            .created(new URI("/api/varietes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /varietes/:id} : Updates an existing variete.
     *
     * @param id the id of the varieteDTO to save.
     * @param varieteDTO the varieteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated varieteDTO,
     * or with status {@code 400 (Bad Request)} if the varieteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the varieteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/varietes/{id}")
    public ResponseEntity<VarieteDTO> updateVariete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VarieteDTO varieteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Variete : {}, {}", id, varieteDTO);
        if (varieteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, varieteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!varieteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VarieteDTO result = varieteService.save(varieteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, varieteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /varietes/:id} : Partial updates given fields of an existing variete, field will ignore if it is null
     *
     * @param id the id of the varieteDTO to save.
     * @param varieteDTO the varieteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated varieteDTO,
     * or with status {@code 400 (Bad Request)} if the varieteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the varieteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the varieteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/varietes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VarieteDTO> partialUpdateVariete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VarieteDTO varieteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Variete partially : {}, {}", id, varieteDTO);
        if (varieteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, varieteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!varieteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VarieteDTO> result = varieteService.partialUpdate(varieteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, varieteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /varietes} : get all the varietes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of varietes in body.
     */
    @GetMapping("/varietes")
    public List<VarieteDTO> getAllVarietes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Varietes");
        return varieteService.findAll();
    }

    /**
     * {@code GET  /varietes/:id} : get the "id" variete.
     *
     * @param id the id of the varieteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varieteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/varietes/{id}")
    public ResponseEntity<VarieteDTO> getVariete(@PathVariable Long id) {
        log.debug("REST request to get Variete : {}", id);
        Optional<VarieteDTO> varieteDTO = varieteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(varieteDTO);
    }

    /**
     * {@code DELETE  /varietes/:id} : delete the "id" variete.
     *
     * @param id the id of the varieteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/varietes/{id}")
    public ResponseEntity<Void> deleteVariete(@PathVariable Long id) {
        log.debug("REST request to delete Variete : {}", id);
        varieteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

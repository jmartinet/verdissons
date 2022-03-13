package fr.tos.perma.web.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.tos.perma.web.repository.EspeceRepository;
import fr.tos.perma.web.service.EspeceQueryService;
import fr.tos.perma.web.service.EspeceService;
import fr.tos.perma.web.service.criteria.EspeceCriteria;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.tos.perma.web.domain.Espece}.
 */
@RestController
@RequestMapping("/api")
public class EspeceResource {

	private final Logger log = LoggerFactory.getLogger(EspeceResource.class);

	private static final String ENTITY_NAME = "espece";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final EspeceService especeService;

	private final EspeceRepository especeRepository;

	private final EspeceQueryService especeQueryService;

	public EspeceResource(EspeceService especeService, EspeceRepository especeRepository,
			EspeceQueryService especeQueryService) {
		this.especeService = especeService;
		this.especeRepository = especeRepository;
		this.especeQueryService = especeQueryService;
	}

	/**
	 * {@code POST  /especes} : Create a new espece.
	 *
	 * @param especeDTO the especeDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new especeDTO, or with status {@code 400 (Bad Request)} if
	 *         the espece has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/especes")
	public ResponseEntity<EspeceDTO> createEspece(@RequestBody EspeceDTO especeDTO) throws URISyntaxException {
		log.debug("REST request to save Espece : {}", especeDTO);
		if (especeDTO.getId() != null) {
			throw new BadRequestAlertException("A new espece cannot already have an ID", ENTITY_NAME, "idexists");
		}
		EspeceDTO result = especeService.save(especeDTO);
		return ResponseEntity
				.created(new URI("/api/especes/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /especes/:id} : Updates an existing espece.
	 *
	 * @param id        the id of the especeDTO to save.
	 * @param especeDTO the especeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated especeDTO, or with status {@code 400 (Bad Request)} if
	 *         the especeDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the especeDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/especes/{id}")
	public ResponseEntity<EspeceDTO> updateEspece(@PathVariable(value = "id", required = false) final Integer id,
			@RequestBody EspeceDTO especeDTO) throws URISyntaxException {
		log.debug("REST request to update Espece : {}, {}", id, especeDTO);
		if (especeDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, especeDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!especeRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		EspeceDTO result = especeService.save(especeDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, especeDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /especes} : get all the especes.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of especes in body.
	 */
	@GetMapping("/especes")
	public ResponseEntity<List<EspeceDTO>> getAllEspeces(EspeceCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Especes by criteria: {}", criteria);
		Page<EspeceDTO> page = especeQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /especes/count} : count all the especes.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/especes/count")
	public ResponseEntity<Long> countEspeces(EspeceCriteria criteria) {
		log.debug("REST request to count Especes by criteria: {}", criteria);
		return ResponseEntity.ok().body(especeQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /especes/:id} : get the "id" espece.
	 *
	 * @param id the id of the especeDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the especeDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/especes/{id}")
	public ResponseEntity<EspeceDTO> getEspece(@PathVariable Integer id) {
		log.debug("REST request to get Espece : {}", id);
		Optional<EspeceDTO> especeDTO = especeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(especeDTO);
	}

	/**
	 * {@code DELETE  /especes/:id} : delete the "id" espece.
	 *
	 * @param id the id of the especeDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/especes/{id}")
	public ResponseEntity<Void> deleteEspece(@PathVariable Integer id) {
		log.debug("REST request to delete Espece : {}", id);
		especeService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}

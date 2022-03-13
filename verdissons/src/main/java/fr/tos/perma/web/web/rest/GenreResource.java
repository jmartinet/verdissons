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

import fr.tos.perma.web.repository.GenreRepository;
import fr.tos.perma.web.service.GenreQueryService;
import fr.tos.perma.web.service.GenreService;
import fr.tos.perma.web.service.criteria.GenreCriteria;
import fr.tos.perma.web.service.dto.GenreDTO;
import fr.tos.perma.web.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.tos.perma.web.domain.Genre}.
 */
@RestController
@RequestMapping("/api")
public class GenreResource {

	private final Logger log = LoggerFactory.getLogger(GenreResource.class);

	private static final String ENTITY_NAME = "genre";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final GenreService genreService;

	private final GenreRepository genreRepository;

	private final GenreQueryService genreQueryService;

	public GenreResource(GenreService genreService, GenreRepository genreRepository,
			GenreQueryService genreQueryService) {
		this.genreService = genreService;
		this.genreRepository = genreRepository;
		this.genreQueryService = genreQueryService;
	}

	/**
	 * {@code POST  /genres} : Create a new genre.
	 *
	 * @param genreDTO the genreDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new genreDTO, or with status {@code 400 (Bad Request)} if
	 *         the genre has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/genres")
	public ResponseEntity<GenreDTO> createGenre(@RequestBody GenreDTO genreDTO) throws URISyntaxException {
		log.debug("REST request to save Genre : {}", genreDTO);
		if (genreDTO.getId() != null) {
			throw new BadRequestAlertException("A new genre cannot already have an ID", ENTITY_NAME, "idexists");
		}
		GenreDTO result = genreService.save(genreDTO);
		return ResponseEntity
				.created(new URI("/api/genres/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /genres/:id} : Updates an existing genre.
	 *
	 * @param id       the id of the genreDTO to save.
	 * @param genreDTO the genreDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated genreDTO, or with status {@code 400 (Bad Request)} if the
	 *         genreDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the genreDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/genres/{id}")
	public ResponseEntity<GenreDTO> updateGenre(@PathVariable(value = "id", required = false) final Integer id,
			@RequestBody GenreDTO genreDTO) throws URISyntaxException {
		log.debug("REST request to update Genre : {}, {}", id, genreDTO);
		if (genreDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, genreDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!genreRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		GenreDTO result = genreService.save(genreDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, genreDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /genres} : get all the genres.
	 *
	 * @param eagerload flag to eager load entities from relationships (This is
	 *                  applicable for many-to-many).
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of genres in body.
	 */
	@GetMapping("/genres")
	public ResponseEntity<List<GenreDTO>> getAllGenres(GenreCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Especes by criteria: {}", criteria);
		Page<GenreDTO> page = genreQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /genres/:id} : get the "id" genre.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/genres/count")
	public ResponseEntity<Long> countGenres(GenreCriteria criteria) {
		log.debug("REST request to count Especes by criteria: {}", criteria);
		return ResponseEntity.ok().body(genreQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /genres/:id} : get the "id" genre.
	 *
	 * @param id the id of the genreDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the genreDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/genres/{id}")
	public ResponseEntity<GenreDTO> getGenre(@PathVariable Integer id) {
		log.debug("REST request to get Genre : {}", id);
		Optional<GenreDTO> genreDTO = genreService.findOne(id);
		return ResponseUtil.wrapOrNotFound(genreDTO);
	}

	/**
	 * {@code DELETE  /genres/:id} : delete the "id" genre.
	 *
	 * @param id the id of the genreDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/genres/{id}")
	public ResponseEntity<Void> deleteGenre(@PathVariable Integer id) {
		log.debug("REST request to delete Genre : {}", id);
		genreService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}

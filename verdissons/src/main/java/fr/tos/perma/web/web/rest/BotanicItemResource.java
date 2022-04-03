package fr.tos.perma.web.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.tos.perma.web.service.BotanicItemQueryService;
import fr.tos.perma.web.service.BotanicItemService;
import fr.tos.perma.web.service.criteria.BotanicItemCriteria;
import fr.tos.perma.web.service.dto.BotanicItemDTO;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class BotanicItemResource {

	private final Logger log = LoggerFactory.getLogger(BotanicItemResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BotanicItemQueryService botanicItemQueryService;

	private final BotanicItemService botanicItemService;

	public BotanicItemResource(BotanicItemQueryService especeQueryService, BotanicItemService botanicItemService) {
		this.botanicItemQueryService = especeQueryService;
		this.botanicItemService = botanicItemService;
	}

	/**
	 * {@code GET  /especes} : get all the especes.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of especes in body.
	 */
	@GetMapping("/botanicItems")
	public ResponseEntity<List<BotanicItemDTO>> getAllEspeces(BotanicItemCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Especes by criteria: {}", criteria);
		Page<BotanicItemDTO> list = botanicItemQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), list);
		return ResponseEntity.ok().headers(headers).body(list.getContent());
	}

    /**
     * {@code GET  /varietes/:id} : get the "id" variete.
     *
     * @param id the id of the varieteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varieteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/botanicItems/espece/{id}")
    public ResponseEntity<BotanicItemDTO> getEspece(@PathVariable Long id) {
        log.debug("REST request to get Botanic Item : {}", id);
        Optional<BotanicItemDTO> botanicItemDTO = botanicItemService.findOneEspece(id);
        return ResponseUtil.wrapOrNotFound(botanicItemDTO);
    }

    /**
     * {@code GET  /varietes/:id} : get the "id" variete.
     *
     * @param id the id of the varieteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varieteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/botanicItems/genre/{id}")
    public ResponseEntity<BotanicItemDTO> getGenre(@PathVariable Long id) {
        log.debug("REST request to get Botanic Item : {}", id);
        Optional<BotanicItemDTO> botanicItemDTO = botanicItemService.findOneGenre(id);
        return ResponseUtil.wrapOrNotFound(botanicItemDTO);
    }

    /**
     * {@code GET  /varietes/:id} : get the "id" variete.
     *
     * @param id the id of the varieteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the varieteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/botanicItems/famille/{id}")
    public ResponseEntity<BotanicItemDTO> getFamille(@PathVariable Long id) {
        log.debug("REST request to get Botanic Item : {}", id);
        Optional<BotanicItemDTO> botanicItemDTO = botanicItemService.findOneFamille(id);
        return ResponseUtil.wrapOrNotFound(botanicItemDTO);
    }

}

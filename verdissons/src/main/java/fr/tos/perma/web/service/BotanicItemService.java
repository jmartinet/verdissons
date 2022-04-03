package fr.tos.perma.web.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.domain.Genre;
import fr.tos.perma.web.repository.BotanicItemRepository;
import fr.tos.perma.web.service.dto.BotanicItemDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;

@Service
@Transactional
public class BotanicItemService {

	private final Logger log = LoggerFactory.getLogger(BotanicItemService.class);

	private final BotanicItemRepository<Espece> especeRepository;

	private final BotanicItemRepository<Genre> genreRepository;

	private final BotanicItemRepository<Famille> familleRepository;

	private final BotanicItemMapper botanicItemMapper;

	public BotanicItemService(BotanicItemRepository<Espece> especeRepository,
			BotanicItemRepository<Genre> genreRepository, BotanicItemRepository<Famille> familleRepository,
			BotanicItemMapper botanicItemMapper) {
		this.especeRepository = especeRepository;
		this.genreRepository = genreRepository;
		this.familleRepository = familleRepository;
		this.botanicItemMapper = botanicItemMapper;
	}

	/**
	 * Get one botanic item by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<BotanicItemDTO> findOneEspece(Long id) {
		log.debug("Request to get Variete : {}", id);
		return especeRepository.findOneWithEagerRelationships(id).map(botanicItemMapper::toDto);
	}

	/**
	 * Get one botanic item by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<BotanicItemDTO> findOneGenre(Long id) {
		log.debug("Request to get Variete : {}", id);
		return genreRepository.findOneWithEagerRelationships(id).map(botanicItemMapper::toDto);
	}

	/**
	 * Get one botanic item by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<BotanicItemDTO> findOneFamille(Long id) {
		log.debug("Request to get Variete : {}", id);
		return familleRepository.findOneWithEagerRelationships(id).map(botanicItemMapper::toDto);
	}

}

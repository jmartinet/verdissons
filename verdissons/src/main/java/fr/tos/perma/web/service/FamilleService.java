package fr.tos.perma.web.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.repository.FamilleRepository;
import fr.tos.perma.web.service.dto.FamilleDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;

/**
 * Service Implementation for managing {@link Famille}.
 */
@Service
@Transactional
public class FamilleService {

	private final Logger log = LoggerFactory.getLogger(FamilleService.class);

	private final FamilleRepository familleRepository;

	private final BotanicItemMapper<Famille, FamilleDTO> familleMapper;

	public FamilleService(FamilleRepository familleRepository) {
		this.familleRepository = familleRepository;
		this.familleMapper = new BotanicItemMapper<Famille, FamilleDTO>();
	}

	/**
	 * Save a famille.
	 *
	 * @param familleDTO the entity to save.
	 * @return the persisted entity.
	 */
	public FamilleDTO save(FamilleDTO familleDTO) {
		log.debug("Request to save Famille : {}", familleDTO);
		Famille famille = familleMapper.toEntity(familleDTO);
		famille = familleRepository.save(famille);
		return familleMapper.toDto(famille);
	}

	/**
	 * Get all the familles.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<FamilleDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Especes");
		return familleRepository.findAll(pageable).map(familleMapper::toDto);
	}

	/**
	 * Get one famille by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<FamilleDTO> findOne(Integer id) {
		log.debug("Request to get Famille : {}", id);
		return familleRepository.findById(id).map(familleMapper::toDto);
	}

	/**
	 * Delete the famille by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Integer id) {
		log.debug("Request to delete Famille : {}", id);
		familleRepository.deleteById(id);
	}
}

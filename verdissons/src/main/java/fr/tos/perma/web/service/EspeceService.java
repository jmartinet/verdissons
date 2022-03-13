package fr.tos.perma.web.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.repository.EspeceRepository;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;

/**
 * Service Implementation for managing {@link Espece}.
 */
@Service
@Transactional
public class EspeceService {

	private final Logger log = LoggerFactory.getLogger(EspeceService.class);

	private final EspeceRepository especeRepository;

	private final BotanicItemMapper<Espece, EspeceDTO> especeMapper;

	public EspeceService(EspeceRepository especeRepository) {
		this.especeRepository = especeRepository;
		this.especeMapper = new BotanicItemMapper<Espece, EspeceDTO>();
	}

	/**
	 * Save a espece.
	 *
	 * @param especeDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EspeceDTO save(EspeceDTO especeDTO) {
		log.debug("Request to save Espece : {}", especeDTO);
		Espece espece = especeMapper.toEntity(especeDTO);
		espece = especeRepository.save(espece);
		return especeMapper.toDto(espece);
	}

	/**
	 * Get all the especes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<EspeceDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Especes");
		return especeRepository.findAll(pageable).map(especeMapper::toDto);
	}

	/**
	 * Get one espece by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<EspeceDTO> findOne(Integer id) {
		log.debug("Request to get Espece : {}", id);
		return especeRepository.findById(id).map(especeMapper::toDto);
	}

	/**
	 * Delete the espece by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Integer id) {
		log.debug("Request to delete Espece : {}", id);
		especeRepository.deleteById(id);
	}
}

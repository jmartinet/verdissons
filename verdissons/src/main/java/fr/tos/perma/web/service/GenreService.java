package fr.tos.perma.web.service;

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

import fr.tos.perma.web.domain.Genre;
import fr.tos.perma.web.repository.GenreRepository;
import fr.tos.perma.web.service.dto.GenreDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;

/**
 * Service Implementation for managing {@link Genre}.
 */
@Service
@Transactional
public class GenreService {

	private final Logger log = LoggerFactory.getLogger(GenreService.class);

	private final GenreRepository genreRepository;

	private final BotanicItemMapper<Genre, GenreDTO> genreMapper;

	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
		this.genreMapper = new BotanicItemMapper<Genre, GenreDTO>();
	}

	/**
	 * Save a genre.
	 *
	 * @param genreDTO the entity to save.
	 * @return the persisted entity.
	 */
	public GenreDTO save(GenreDTO genreDTO) {
		log.debug("Request to save Genre : {}", genreDTO);
		Genre genre = genreMapper.toEntity(genreDTO);
		genre = genreRepository.save(genre);
		return genreMapper.toDto(genre);
	}

	/**
	 * Get all the genres.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		log.debug("Request to get all Genres");
		return genreRepository.findAllWithEagerRelationships().stream().map(genreMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get all the genres with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	public Page<GenreDTO> findAllWithEagerRelationships(Pageable pageable) {
		return genreRepository.findAllWithEagerRelationships(pageable).map(genreMapper::toDto);
	}

	/**
	 * Get one genre by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<GenreDTO> findOne(Integer id) {
		log.debug("Request to get Genre : {}", id);
		return genreRepository.findOneWithEagerRelationships(id).map(genreMapper::toDto);
	}

	/**
	 * Delete the genre by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Integer id) {
		log.debug("Request to delete Genre : {}", id);
		genreRepository.deleteById(id);
	}
}

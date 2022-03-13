package fr.tos.perma.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.IntegrationTest;
import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.repository.EspeceRepository;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.service.mapper.BotanicItemMapper;

/**
 * Integration tests for the {@link EspeceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspeceResourceIT {

	private static final String ENTITY_API_URL = "/api/especes";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

	private static Random random = new Random();
	private static AtomicInteger count = new AtomicInteger(random.nextInt() + (2 * Integer.MAX_VALUE));

	@Autowired
	private EspeceRepository especeRepository;

	@Autowired
	private BotanicItemMapper<Espece, EspeceDTO> especeMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restEspeceMockMvc;

	private Espece espece;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Espece createEntity(EntityManager em) {
		Espece espece = new Espece();
		return espece;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Espece createUpdatedEntity(EntityManager em) {
		Espece espece = new Espece();
		return espece;
	}

	@BeforeEach
	public void initTest() {
		espece = createEntity(em);
	}

	@Test
	@Transactional
	void createEspece() throws Exception {
		int databaseSizeBeforeCreate = especeRepository.findAll().size();
		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);
		restEspeceMockMvc.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(especeDTO))).andExpect(status().isCreated());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeCreate + 1);
		Espece testEspece = especeList.get(especeList.size() - 1);
	}

	@Test
	@Transactional
	void createEspeceWithExistingId() throws Exception {
		// Create the Espece with an existing ID
		espece.setId(1);
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		int databaseSizeBeforeCreate = especeRepository.findAll().size();

		// An entity with an existing ID cannot be created, so this API call must fail
		restEspeceMockMvc.perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(especeDTO))).andExpect(status().isBadRequest());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	void getAllEspeces() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		// Get all the especeList
		restEspeceMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(espece.getId().intValue())));
	}

	@Test
	@Transactional
	void getEspece() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		// Get the espece
		restEspeceMockMvc.perform(get(ENTITY_API_URL_ID, espece.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(espece.getId().intValue()));
	}

	@Test
	@Transactional
	void getEspecesByIdFiltering() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		Integer id = espece.getId();

		defaultEspeceShouldBeFound("id.equals=" + id);
		defaultEspeceShouldNotBeFound("id.notEquals=" + id);

		defaultEspeceShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultEspeceShouldNotBeFound("id.greaterThan=" + id);

		defaultEspeceShouldBeFound("id.lessThanOrEqual=" + id);
		defaultEspeceShouldNotBeFound("id.lessThan=" + id);
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultEspeceShouldBeFound(String filter) throws Exception {
		restEspeceMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(espece.getId().intValue())));

		// Check, that the count call also returns 1
		restEspeceMockMvc.perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultEspeceShouldNotBeFound(String filter) throws Exception {
		restEspeceMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restEspeceMockMvc.perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	void getNonExistingEspece() throws Exception {
		// Get the espece
		restEspeceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	void putNewEspece() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		int databaseSizeBeforeUpdate = especeRepository.findAll().size();

		// Update the espece
		Espece updatedEspece = especeRepository.findById(espece.getId()).get();
		// Disconnect from session so that the updates on updatedEspece are not directly
		// saved in db
		em.detach(updatedEspece);
		EspeceDTO especeDTO = especeMapper.toDto(updatedEspece);

		restEspeceMockMvc.perform(put(ENTITY_API_URL_ID, especeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(especeDTO))).andExpect(status().isOk());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
		Espece testEspece = especeList.get(especeList.size() - 1);
	}

	@Test
	@Transactional
	void putNonExistingEspece() throws Exception {
		int databaseSizeBeforeUpdate = especeRepository.findAll().size();
		espece.setId(count.incrementAndGet());

		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restEspeceMockMvc.perform(put(ENTITY_API_URL_ID, especeDTO.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(especeDTO))).andExpect(status().isBadRequest());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void putWithIdMismatchEspece() throws Exception {
		int databaseSizeBeforeUpdate = especeRepository.findAll().size();
		espece.setId(count.incrementAndGet());

		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEspeceMockMvc.perform(put(ENTITY_API_URL_ID, count.incrementAndGet())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void putWithMissingIdPathParamEspece() throws Exception {
		int databaseSizeBeforeUpdate = especeRepository.findAll().size();
		espece.setId(count.incrementAndGet());

		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEspeceMockMvc
				.perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(especeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void partialUpdateEspeceWithPatch() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		int databaseSizeBeforeUpdate = especeRepository.findAll().size();

		// Update the espece using partial update
		Espece partialUpdatedEspece = new Espece();
		partialUpdatedEspece.setId(espece.getId());

		restEspeceMockMvc.perform(
				patch(ENTITY_API_URL_ID, partialUpdatedEspece.getId()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspece)))
				.andExpect(status().isOk());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
		Espece testEspece = especeList.get(especeList.size() - 1);
	}

	@Test
	@Transactional
	void fullUpdateEspeceWithPatch() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		int databaseSizeBeforeUpdate = especeRepository.findAll().size();

		// Update the espece using partial update
		Espece partialUpdatedEspece = new Espece();
		partialUpdatedEspece.setId(espece.getId());

		restEspeceMockMvc.perform(
				patch(ENTITY_API_URL_ID, partialUpdatedEspece.getId()).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspece)))
				.andExpect(status().isOk());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
		Espece testEspece = especeList.get(especeList.size() - 1);
	}

	@Test
	@Transactional
	void patchNonExistingEspece() throws Exception {
		int databaseSizeBeforeUpdate = especeRepository.findAll().size();
		espece.setId(count.incrementAndGet());

		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restEspeceMockMvc.perform(patch(ENTITY_API_URL_ID, especeDTO.getId())
				.contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(especeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void patchWithIdMismatchEspece() throws Exception {
		int databaseSizeBeforeUpdate = especeRepository.findAll().size();
		espece.setId(count.incrementAndGet());

		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEspeceMockMvc.perform(patch(ENTITY_API_URL_ID, count.incrementAndGet())
				.contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(especeDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void patchWithMissingIdPathParamEspece() throws Exception {
		int databaseSizeBeforeUpdate = especeRepository.findAll().size();
		espece.setId(count.incrementAndGet());

		// Create the Espece
		EspeceDTO especeDTO = especeMapper.toDto(espece);

		// If url ID doesn't match entity ID, it will throw BadRequestAlertException
		restEspeceMockMvc
				.perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json")
						.content(TestUtil.convertObjectToJsonBytes(especeDTO)))
				.andExpect(status().isMethodNotAllowed());

		// Validate the Espece in the database
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	void deleteEspece() throws Exception {
		// Initialize the database
		especeRepository.saveAndFlush(espece);

		int databaseSizeBeforeDelete = especeRepository.findAll().size();

		// Delete the espece
		restEspeceMockMvc.perform(delete(ENTITY_API_URL_ID, espece.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Espece> especeList = especeRepository.findAll();
		assertThat(especeList).hasSize(databaseSizeBeforeDelete - 1);
	}
}

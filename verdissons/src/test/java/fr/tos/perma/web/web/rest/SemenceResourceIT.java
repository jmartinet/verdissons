package fr.tos.perma.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tos.perma.web.IntegrationTest;
import fr.tos.perma.web.domain.Semence;
import fr.tos.perma.web.repository.SemenceRepository;
import fr.tos.perma.web.service.dto.SemenceDTO;
import fr.tos.perma.web.service.mapper.SemenceMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SemenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SemenceResourceIT {

    private static final String DEFAULT_TYPE_SEMIS = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SEMIS = "BBBBBBBBBB";

    private static final String DEFAULT_CONSEIL_SEMIS = "AAAAAAAAAA";
    private static final String UPDATED_CONSEIL_SEMIS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PERIODE_SEMIS_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIODE_SEMIS_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIODE_SEMIS_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIODE_SEMIS_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/semences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SemenceRepository semenceRepository;

    @Autowired
    private SemenceMapper semenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemenceMockMvc;

    private Semence semence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semence createEntity(EntityManager em) {
        Semence semence = new Semence()
            .typeSemis(DEFAULT_TYPE_SEMIS)
            .conseilSemis(DEFAULT_CONSEIL_SEMIS)
            .periodeSemisDebut(DEFAULT_PERIODE_SEMIS_DEBUT)
            .periodeSemisFin(DEFAULT_PERIODE_SEMIS_FIN);
        return semence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semence createUpdatedEntity(EntityManager em) {
        Semence semence = new Semence()
            .typeSemis(UPDATED_TYPE_SEMIS)
            .conseilSemis(UPDATED_CONSEIL_SEMIS)
            .periodeSemisDebut(UPDATED_PERIODE_SEMIS_DEBUT)
            .periodeSemisFin(UPDATED_PERIODE_SEMIS_FIN);
        return semence;
    }

    @BeforeEach
    public void initTest() {
        semence = createEntity(em);
    }

    @Test
    @Transactional
    void createSemence() throws Exception {
        int databaseSizeBeforeCreate = semenceRepository.findAll().size();
        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);
        restSemenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeCreate + 1);
        Semence testSemence = semenceList.get(semenceList.size() - 1);
        assertThat(testSemence.getTypeSemis()).isEqualTo(DEFAULT_TYPE_SEMIS);
        assertThat(testSemence.getConseilSemis()).isEqualTo(DEFAULT_CONSEIL_SEMIS);
        assertThat(testSemence.getPeriodeSemisDebut()).isEqualTo(DEFAULT_PERIODE_SEMIS_DEBUT);
        assertThat(testSemence.getPeriodeSemisFin()).isEqualTo(DEFAULT_PERIODE_SEMIS_FIN);
    }

    @Test
    @Transactional
    void createSemenceWithExistingId() throws Exception {
        // Create the Semence with an existing ID
        semence.setId(1L);
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        int databaseSizeBeforeCreate = semenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSemences() throws Exception {
        // Initialize the database
        semenceRepository.saveAndFlush(semence);

        // Get all the semenceList
        restSemenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semence.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeSemis").value(hasItem(DEFAULT_TYPE_SEMIS)))
            .andExpect(jsonPath("$.[*].conseilSemis").value(hasItem(DEFAULT_CONSEIL_SEMIS)))
            .andExpect(jsonPath("$.[*].periodeSemisDebut").value(hasItem(DEFAULT_PERIODE_SEMIS_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].periodeSemisFin").value(hasItem(DEFAULT_PERIODE_SEMIS_FIN.toString())));
    }

    @Test
    @Transactional
    void getSemence() throws Exception {
        // Initialize the database
        semenceRepository.saveAndFlush(semence);

        // Get the semence
        restSemenceMockMvc
            .perform(get(ENTITY_API_URL_ID, semence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semence.getId().intValue()))
            .andExpect(jsonPath("$.typeSemis").value(DEFAULT_TYPE_SEMIS))
            .andExpect(jsonPath("$.conseilSemis").value(DEFAULT_CONSEIL_SEMIS))
            .andExpect(jsonPath("$.periodeSemisDebut").value(DEFAULT_PERIODE_SEMIS_DEBUT.toString()))
            .andExpect(jsonPath("$.periodeSemisFin").value(DEFAULT_PERIODE_SEMIS_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSemence() throws Exception {
        // Get the semence
        restSemenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSemence() throws Exception {
        // Initialize the database
        semenceRepository.saveAndFlush(semence);

        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();

        // Update the semence
        Semence updatedSemence = semenceRepository.findById(semence.getId()).get();
        // Disconnect from session so that the updates on updatedSemence are not directly saved in db
        em.detach(updatedSemence);
        updatedSemence
            .typeSemis(UPDATED_TYPE_SEMIS)
            .conseilSemis(UPDATED_CONSEIL_SEMIS)
            .periodeSemisDebut(UPDATED_PERIODE_SEMIS_DEBUT)
            .periodeSemisFin(UPDATED_PERIODE_SEMIS_FIN);
        SemenceDTO semenceDTO = semenceMapper.toDto(updatedSemence);

        restSemenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, semenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
        Semence testSemence = semenceList.get(semenceList.size() - 1);
        assertThat(testSemence.getTypeSemis()).isEqualTo(UPDATED_TYPE_SEMIS);
        assertThat(testSemence.getConseilSemis()).isEqualTo(UPDATED_CONSEIL_SEMIS);
        assertThat(testSemence.getPeriodeSemisDebut()).isEqualTo(UPDATED_PERIODE_SEMIS_DEBUT);
        assertThat(testSemence.getPeriodeSemisFin()).isEqualTo(UPDATED_PERIODE_SEMIS_FIN);
    }

    @Test
    @Transactional
    void putNonExistingSemence() throws Exception {
        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();
        semence.setId(count.incrementAndGet());

        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, semenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSemence() throws Exception {
        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();
        semence.setId(count.incrementAndGet());

        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSemence() throws Exception {
        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();
        semence.setId(count.incrementAndGet());

        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSemenceWithPatch() throws Exception {
        // Initialize the database
        semenceRepository.saveAndFlush(semence);

        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();

        // Update the semence using partial update
        Semence partialUpdatedSemence = new Semence();
        partialUpdatedSemence.setId(semence.getId());

        partialUpdatedSemence.typeSemis(UPDATED_TYPE_SEMIS);

        restSemenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSemence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSemence))
            )
            .andExpect(status().isOk());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
        Semence testSemence = semenceList.get(semenceList.size() - 1);
        assertThat(testSemence.getTypeSemis()).isEqualTo(UPDATED_TYPE_SEMIS);
        assertThat(testSemence.getConseilSemis()).isEqualTo(DEFAULT_CONSEIL_SEMIS);
        assertThat(testSemence.getPeriodeSemisDebut()).isEqualTo(DEFAULT_PERIODE_SEMIS_DEBUT);
        assertThat(testSemence.getPeriodeSemisFin()).isEqualTo(DEFAULT_PERIODE_SEMIS_FIN);
    }

    @Test
    @Transactional
    void fullUpdateSemenceWithPatch() throws Exception {
        // Initialize the database
        semenceRepository.saveAndFlush(semence);

        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();

        // Update the semence using partial update
        Semence partialUpdatedSemence = new Semence();
        partialUpdatedSemence.setId(semence.getId());

        partialUpdatedSemence
            .typeSemis(UPDATED_TYPE_SEMIS)
            .conseilSemis(UPDATED_CONSEIL_SEMIS)
            .periodeSemisDebut(UPDATED_PERIODE_SEMIS_DEBUT)
            .periodeSemisFin(UPDATED_PERIODE_SEMIS_FIN);

        restSemenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSemence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSemence))
            )
            .andExpect(status().isOk());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
        Semence testSemence = semenceList.get(semenceList.size() - 1);
        assertThat(testSemence.getTypeSemis()).isEqualTo(UPDATED_TYPE_SEMIS);
        assertThat(testSemence.getConseilSemis()).isEqualTo(UPDATED_CONSEIL_SEMIS);
        assertThat(testSemence.getPeriodeSemisDebut()).isEqualTo(UPDATED_PERIODE_SEMIS_DEBUT);
        assertThat(testSemence.getPeriodeSemisFin()).isEqualTo(UPDATED_PERIODE_SEMIS_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingSemence() throws Exception {
        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();
        semence.setId(count.incrementAndGet());

        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, semenceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSemence() throws Exception {
        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();
        semence.setId(count.incrementAndGet());

        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSemence() throws Exception {
        int databaseSizeBeforeUpdate = semenceRepository.findAll().size();
        semence.setId(count.incrementAndGet());

        // Create the Semence
        SemenceDTO semenceDTO = semenceMapper.toDto(semence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(semenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Semence in the database
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSemence() throws Exception {
        // Initialize the database
        semenceRepository.saveAndFlush(semence);

        int databaseSizeBeforeDelete = semenceRepository.findAll().size();

        // Delete the semence
        restSemenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, semence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semence> semenceList = semenceRepository.findAll();
        assertThat(semenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package fr.tos.perma.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tos.perma.web.IntegrationTest;
import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.repository.VarieteRepository;
import fr.tos.perma.web.service.dto.VarieteDTO;
import fr.tos.perma.web.service.mapper.VarieteMapper;
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
 * Integration tests for the {@link VarieteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VarieteResourceIT {

    private static final String DEFAULT_NOM_ = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ = "BBBBBBBBBB";

    private static final String DEFAULT_CONSEIL_CULTURE = "AAAAAAAAAA";
    private static final String UPDATED_CONSEIL_CULTURE = "BBBBBBBBBB";

    private static final String DEFAULT_CULTURE = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPOSITION = "AAAAAAAAAA";
    private static final String UPDATED_EXPOSITION = "BBBBBBBBBB";

    private static final String DEFAULT_BESOIN_EAU = "AAAAAAAAAA";
    private static final String UPDATED_BESOIN_EAU = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE_SOL = "AAAAAAAAAA";
    private static final String UPDATED_NATURE_SOL = "BBBBBBBBBB";

    private static final String DEFAULT_QUALITE_SOL = "AAAAAAAAAA";
    private static final String UPDATED_QUALITE_SOL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/varietes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VarieteRepository varieteRepository;

    @Autowired
    private VarieteMapper varieteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVarieteMockMvc;

    private Variete variete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variete createEntity(EntityManager em) {
        Variete variete = new Variete()
            .nom(DEFAULT_NOM_)
            .conseilCulture(DEFAULT_CONSEIL_CULTURE)
            .culture(DEFAULT_CULTURE)
            .exposition(DEFAULT_EXPOSITION)
            .besoinEau(DEFAULT_BESOIN_EAU)
            .natureSol(DEFAULT_NATURE_SOL)
            .qualiteSol(DEFAULT_QUALITE_SOL);
        return variete;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variete createUpdatedEntity(EntityManager em) {
        Variete variete = new Variete()
            .nom(UPDATED_NOM_)
            .conseilCulture(UPDATED_CONSEIL_CULTURE)
            .culture(UPDATED_CULTURE)
            .exposition(UPDATED_EXPOSITION)
            .besoinEau(UPDATED_BESOIN_EAU)
            .natureSol(UPDATED_NATURE_SOL)
            .qualiteSol(UPDATED_QUALITE_SOL);
        return variete;
    }

    @BeforeEach
    public void initTest() {
        variete = createEntity(em);
    }

    @Test
    @Transactional
    void createVariete() throws Exception {
        int databaseSizeBeforeCreate = varieteRepository.findAll().size();
        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);
        restVarieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varieteDTO)))
            .andExpect(status().isCreated());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeCreate + 1);
        Variete testVariete = varieteList.get(varieteList.size() - 1);
        assertThat(testVariete.getNom()).isEqualTo(DEFAULT_NOM_);
        assertThat(testVariete.getConseilCulture()).isEqualTo(DEFAULT_CONSEIL_CULTURE);
        assertThat(testVariete.getCulture()).isEqualTo(DEFAULT_CULTURE);
        assertThat(testVariete.getExposition()).isEqualTo(DEFAULT_EXPOSITION);
        assertThat(testVariete.getBesoinEau()).isEqualTo(DEFAULT_BESOIN_EAU);
        assertThat(testVariete.getNatureSol()).isEqualTo(DEFAULT_NATURE_SOL);
        assertThat(testVariete.getQualiteSol()).isEqualTo(DEFAULT_QUALITE_SOL);
    }

    @Test
    @Transactional
    void createVarieteWithExistingId() throws Exception {
        // Create the Variete with an existing ID
        variete.setId(1L);
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        int databaseSizeBeforeCreate = varieteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varieteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVarietes() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        // Get all the varieteList
        restVarieteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variete.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM_)))
            .andExpect(jsonPath("$.[*].conseilCulture").value(hasItem(DEFAULT_CONSEIL_CULTURE)))
            .andExpect(jsonPath("$.[*].culture").value(hasItem(DEFAULT_CULTURE)))
            .andExpect(jsonPath("$.[*].exposition").value(hasItem(DEFAULT_EXPOSITION)))
            .andExpect(jsonPath("$.[*].besoinEau").value(hasItem(DEFAULT_BESOIN_EAU)))
            .andExpect(jsonPath("$.[*].natureSol").value(hasItem(DEFAULT_NATURE_SOL)))
            .andExpect(jsonPath("$.[*].qualiteSol").value(hasItem(DEFAULT_QUALITE_SOL)));
    }

    @Test
    @Transactional
    void getVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        // Get the variete
        restVarieteMockMvc
            .perform(get(ENTITY_API_URL_ID, variete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(variete.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM_))
            .andExpect(jsonPath("$.conseilCulture").value(DEFAULT_CONSEIL_CULTURE))
            .andExpect(jsonPath("$.culture").value(DEFAULT_CULTURE))
            .andExpect(jsonPath("$.exposition").value(DEFAULT_EXPOSITION))
            .andExpect(jsonPath("$.besoinEau").value(DEFAULT_BESOIN_EAU))
            .andExpect(jsonPath("$.natureSol").value(DEFAULT_NATURE_SOL))
            .andExpect(jsonPath("$.qualiteSol").value(DEFAULT_QUALITE_SOL));
    }

    @Test
    @Transactional
    void getNonExistingVariete() throws Exception {
        // Get the variete
        restVarieteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();

        // Update the variete
        Variete updatedVariete = varieteRepository.findById(variete.getId()).get();
        // Disconnect from session so that the updates on updatedVariete are not directly saved in db
        em.detach(updatedVariete);
        updatedVariete
            .nom(UPDATED_NOM_)
            .conseilCulture(UPDATED_CONSEIL_CULTURE)
            .culture(UPDATED_CULTURE)
            .exposition(UPDATED_EXPOSITION)
            .besoinEau(UPDATED_BESOIN_EAU)
            .natureSol(UPDATED_NATURE_SOL)
            .qualiteSol(UPDATED_QUALITE_SOL);
        VarieteDTO varieteDTO = varieteMapper.toDto(updatedVariete);

        restVarieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, varieteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(varieteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
        Variete testVariete = varieteList.get(varieteList.size() - 1);
        assertThat(testVariete.getNom()).isEqualTo(UPDATED_NOM_);
        assertThat(testVariete.getConseilCulture()).isEqualTo(UPDATED_CONSEIL_CULTURE);
        assertThat(testVariete.getCulture()).isEqualTo(UPDATED_CULTURE);
        assertThat(testVariete.getExposition()).isEqualTo(UPDATED_EXPOSITION);
        assertThat(testVariete.getBesoinEau()).isEqualTo(UPDATED_BESOIN_EAU);
        assertThat(testVariete.getNatureSol()).isEqualTo(UPDATED_NATURE_SOL);
        assertThat(testVariete.getQualiteSol()).isEqualTo(UPDATED_QUALITE_SOL);
    }

    @Test
    @Transactional
    void putNonExistingVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();
        variete.setId(count.incrementAndGet());

        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, varieteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(varieteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();
        variete.setId(count.incrementAndGet());

        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(varieteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();
        variete.setId(count.incrementAndGet());

        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarieteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(varieteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVarieteWithPatch() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();

        // Update the variete using partial update
        Variete partialUpdatedVariete = new Variete();
        partialUpdatedVariete.setId(variete.getId());

        partialUpdatedVariete
            .nom(UPDATED_NOM_)
            .conseilCulture(UPDATED_CONSEIL_CULTURE)
            .culture(UPDATED_CULTURE)
            .exposition(UPDATED_EXPOSITION)
            .besoinEau(UPDATED_BESOIN_EAU);

        restVarieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVariete))
            )
            .andExpect(status().isOk());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
        Variete testVariete = varieteList.get(varieteList.size() - 1);
        assertThat(testVariete.getNom()).isEqualTo(UPDATED_NOM_);
        assertThat(testVariete.getConseilCulture()).isEqualTo(UPDATED_CONSEIL_CULTURE);
        assertThat(testVariete.getCulture()).isEqualTo(UPDATED_CULTURE);
        assertThat(testVariete.getExposition()).isEqualTo(UPDATED_EXPOSITION);
        assertThat(testVariete.getBesoinEau()).isEqualTo(UPDATED_BESOIN_EAU);
        assertThat(testVariete.getNatureSol()).isEqualTo(DEFAULT_NATURE_SOL);
        assertThat(testVariete.getQualiteSol()).isEqualTo(DEFAULT_QUALITE_SOL);
    }

    @Test
    @Transactional
    void fullUpdateVarieteWithPatch() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();

        // Update the variete using partial update
        Variete partialUpdatedVariete = new Variete();
        partialUpdatedVariete.setId(variete.getId());

        partialUpdatedVariete
            .nom(UPDATED_NOM_)
            .conseilCulture(UPDATED_CONSEIL_CULTURE)
            .culture(UPDATED_CULTURE)
            .exposition(UPDATED_EXPOSITION)
            .besoinEau(UPDATED_BESOIN_EAU)
            .natureSol(UPDATED_NATURE_SOL)
            .qualiteSol(UPDATED_QUALITE_SOL);

        restVarieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVariete))
            )
            .andExpect(status().isOk());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
        Variete testVariete = varieteList.get(varieteList.size() - 1);
        assertThat(testVariete.getNom()).isEqualTo(UPDATED_NOM_);
        assertThat(testVariete.getConseilCulture()).isEqualTo(UPDATED_CONSEIL_CULTURE);
        assertThat(testVariete.getCulture()).isEqualTo(UPDATED_CULTURE);
        assertThat(testVariete.getExposition()).isEqualTo(UPDATED_EXPOSITION);
        assertThat(testVariete.getBesoinEau()).isEqualTo(UPDATED_BESOIN_EAU);
        assertThat(testVariete.getNatureSol()).isEqualTo(UPDATED_NATURE_SOL);
        assertThat(testVariete.getQualiteSol()).isEqualTo(UPDATED_QUALITE_SOL);
    }

    @Test
    @Transactional
    void patchNonExistingVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();
        variete.setId(count.incrementAndGet());

        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, varieteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(varieteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();
        variete.setId(count.incrementAndGet());

        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(varieteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();
        variete.setId(count.incrementAndGet());

        // Create the Variete
        VarieteDTO varieteDTO = varieteMapper.toDto(variete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVarieteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(varieteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        int databaseSizeBeforeDelete = varieteRepository.findAll().size();

        // Delete the variete
        restVarieteMockMvc
            .perform(delete(ENTITY_API_URL_ID, variete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

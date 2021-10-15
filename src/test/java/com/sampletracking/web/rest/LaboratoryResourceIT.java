package com.sampletracking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sampletracking.IntegrationTest;
import com.sampletracking.domain.Laboratory;
import com.sampletracking.repository.LaboratoryRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link LaboratoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LaboratoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/laboratories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLaboratoryMockMvc;

    private Laboratory laboratory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratory createEntity(EntityManager em) {
        Laboratory laboratory = new Laboratory()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .code(DEFAULT_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return laboratory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratory createUpdatedEntity(EntityManager em) {
        Laboratory laboratory = new Laboratory()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .code(UPDATED_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return laboratory;
    }

    @BeforeEach
    public void initTest() {
        laboratory = createEntity(em);
    }

    @Test
    @Transactional
    void createLaboratory() throws Exception {
        int databaseSizeBeforeCreate = laboratoryRepository.findAll().size();
        // Create the Laboratory
        restLaboratoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratory)))
            .andExpect(status().isCreated());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeCreate + 1);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLaboratory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLaboratory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLaboratory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLaboratory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLaboratory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLaboratory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createLaboratoryWithExistingId() throws Exception {
        // Create the Laboratory with an existing ID
        laboratory.setId("existing_id");

        int databaseSizeBeforeCreate = laboratoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratory)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLaboratories() throws Exception {
        // Initialize the database
        laboratory.setId(UUID.randomUUID().toString());
        laboratoryRepository.saveAndFlush(laboratory);

        // Get all the laboratoryList
        restLaboratoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratory.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getLaboratory() throws Exception {
        // Initialize the database
        laboratory.setId(UUID.randomUUID().toString());
        laboratoryRepository.saveAndFlush(laboratory);

        // Get the laboratory
        restLaboratoryMockMvc
            .perform(get(ENTITY_API_URL_ID, laboratory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(laboratory.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLaboratory() throws Exception {
        // Get the laboratory
        restLaboratoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLaboratory() throws Exception {
        // Initialize the database
        laboratory.setId(UUID.randomUUID().toString());
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();

        // Update the laboratory
        Laboratory updatedLaboratory = laboratoryRepository.findById(laboratory.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratory are not directly saved in db
        em.detach(updatedLaboratory);
        updatedLaboratory
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .code(UPDATED_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restLaboratoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLaboratory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLaboratory))
            )
            .andExpect(status().isOk());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLaboratory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLaboratory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLaboratory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLaboratory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLaboratory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLaboratory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, laboratory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(laboratory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(laboratory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLaboratoryWithPatch() throws Exception {
        // Initialize the database
        laboratory.setId(UUID.randomUUID().toString());
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();

        // Update the laboratory using partial update
        Laboratory partialUpdatedLaboratory = new Laboratory();
        partialUpdatedLaboratory.setId(laboratory.getId());

        partialUpdatedLaboratory.createdBy(UPDATED_CREATED_BY).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratory))
            )
            .andExpect(status().isOk());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLaboratory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLaboratory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLaboratory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLaboratory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLaboratory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLaboratory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateLaboratoryWithPatch() throws Exception {
        // Initialize the database
        laboratory.setId(UUID.randomUUID().toString());
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();

        // Update the laboratory using partial update
        Laboratory partialUpdatedLaboratory = new Laboratory();
        partialUpdatedLaboratory.setId(laboratory.getId());

        partialUpdatedLaboratory
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .code(UPDATED_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLaboratory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLaboratory))
            )
            .andExpect(status().isOk());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
        Laboratory testLaboratory = laboratoryList.get(laboratoryList.size() - 1);
        assertThat(testLaboratory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLaboratory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLaboratory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLaboratory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLaboratory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLaboratory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLaboratory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, laboratory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(laboratory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLaboratory() throws Exception {
        int databaseSizeBeforeUpdate = laboratoryRepository.findAll().size();
        laboratory.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLaboratoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(laboratory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Laboratory in the database
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLaboratory() throws Exception {
        // Initialize the database
        laboratory.setId(UUID.randomUUID().toString());
        laboratoryRepository.saveAndFlush(laboratory);

        int databaseSizeBeforeDelete = laboratoryRepository.findAll().size();

        // Delete the laboratory
        restLaboratoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, laboratory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        assertThat(laboratoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.sampletracking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sampletracking.IntegrationTest;
import com.sampletracking.domain.TestType;
import com.sampletracking.repository.TestTypeRepository;
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
 * Integration tests for the {@link TestTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TestTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/test-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TestTypeRepository testTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestTypeMockMvc;

    private TestType testType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestType createEntity(EntityManager em) {
        TestType testType = new TestType()
            .name(DEFAULT_NAME)
            .prefix(DEFAULT_PREFIX)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateModified(DEFAULT_DATE_MODIFIED);
        return testType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestType createUpdatedEntity(EntityManager em) {
        TestType testType = new TestType()
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED);
        return testType;
    }

    @BeforeEach
    public void initTest() {
        testType = createEntity(em);
    }

    @Test
    @Transactional
    void createTestType() throws Exception {
        int databaseSizeBeforeCreate = testTypeRepository.findAll().size();
        // Create the TestType
        restTestTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testType)))
            .andExpect(status().isCreated());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TestType testTestType = testTypeList.get(testTypeList.size() - 1);
        assertThat(testTestType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTestType.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testTestType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTestType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTestType.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTestType.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void createTestTypeWithExistingId() throws Exception {
        // Create the TestType with an existing ID
        testType.setId("existing_id");

        int databaseSizeBeforeCreate = testTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testType)))
            .andExpect(status().isBadRequest());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTestTypes() throws Exception {
        // Initialize the database
        testType.setId(UUID.randomUUID().toString());
        testTypeRepository.saveAndFlush(testType);

        // Get all the testTypeList
        restTestTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testType.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())));
    }

    @Test
    @Transactional
    void getTestType() throws Exception {
        // Initialize the database
        testType.setId(UUID.randomUUID().toString());
        testTypeRepository.saveAndFlush(testType);

        // Get the testType
        restTestTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, testType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testType.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTestType() throws Exception {
        // Get the testType
        restTestTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTestType() throws Exception {
        // Initialize the database
        testType.setId(UUID.randomUUID().toString());
        testTypeRepository.saveAndFlush(testType);

        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();

        // Update the testType
        TestType updatedTestType = testTypeRepository.findById(testType.getId()).get();
        // Disconnect from session so that the updates on updatedTestType are not directly saved in db
        em.detach(updatedTestType);
        updatedTestType
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTestTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTestType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTestType))
            )
            .andExpect(status().isOk());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
        TestType testTestType = testTypeList.get(testTypeList.size() - 1);
        assertThat(testTestType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTestType.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testTestType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTestType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTestType.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTestType.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingTestType() throws Exception {
        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();
        testType.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, testType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testType))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTestType() throws Exception {
        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();
        testType.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(testType))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTestType() throws Exception {
        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();
        testType.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTestTypeWithPatch() throws Exception {
        // Initialize the database
        testType.setId(UUID.randomUUID().toString());
        testTypeRepository.saveAndFlush(testType);

        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();

        // Update the testType using partial update
        TestType partialUpdatedTestType = new TestType();
        partialUpdatedTestType.setId(testType.getId());

        partialUpdatedTestType.createdBy(UPDATED_CREATED_BY).dateCreated(UPDATED_DATE_CREATED).dateModified(UPDATED_DATE_MODIFIED);

        restTestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestType))
            )
            .andExpect(status().isOk());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
        TestType testTestType = testTypeList.get(testTypeList.size() - 1);
        assertThat(testTestType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTestType.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testTestType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTestType.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTestType.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTestType.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateTestTypeWithPatch() throws Exception {
        // Initialize the database
        testType.setId(UUID.randomUUID().toString());
        testTypeRepository.saveAndFlush(testType);

        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();

        // Update the testType using partial update
        TestType partialUpdatedTestType = new TestType();
        partialUpdatedTestType.setId(testType.getId());

        partialUpdatedTestType
            .name(UPDATED_NAME)
            .prefix(UPDATED_PREFIX)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTestType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTestType))
            )
            .andExpect(status().isOk());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
        TestType testTestType = testTypeList.get(testTypeList.size() - 1);
        assertThat(testTestType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTestType.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testTestType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTestType.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTestType.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTestType.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingTestType() throws Exception {
        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();
        testType.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, testType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testType))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTestType() throws Exception {
        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();
        testType.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(testType))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTestType() throws Exception {
        int databaseSizeBeforeUpdate = testTypeRepository.findAll().size();
        testType.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTestTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(testType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TestType in the database
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTestType() throws Exception {
        // Initialize the database
        testType.setId(UUID.randomUUID().toString());
        testTypeRepository.saveAndFlush(testType);

        int databaseSizeBeforeDelete = testTypeRepository.findAll().size();

        // Delete the testType
        restTestTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, testType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestType> testTypeList = testTypeRepository.findAll();
        assertThat(testTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

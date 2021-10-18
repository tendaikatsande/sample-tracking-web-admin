package com.sampletracking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sampletracking.IntegrationTest;
import com.sampletracking.domain.Sample;
import com.sampletracking.repository.SampleRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link SampleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SampleResourceIT {

    private static final String DEFAULT_APP_ID = "AAAAAAAAAA";
    private static final String UPDATED_APP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_SAMPLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_SAMPLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_PATIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PATIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LAB_ID = "AAAAAAAAAA";
    private static final String UPDATED_LAB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SAMPLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEST_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_COLLECTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_COLLECTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLLECTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SYNCED = false;
    private static final Boolean UPDATED_SYNCED = true;

    private static final Instant DEFAULT_DATE_SYNCED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_SYNCED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAB_REFERENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LAB_REFERENCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT_RECEIVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_RESULT_RECEIVED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE_AT_HUB = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE_AT_HUB = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE_AT_LAB = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE_AT_LAB = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_MODIFIED_BY_HUB = false;
    private static final Boolean UPDATED_IS_MODIFIED_BY_HUB = true;

    private static final Boolean DEFAULT_IS_MODIFIED_BY_FACILITY = false;
    private static final Boolean UPDATED_IS_MODIFIED_BY_FACILITY = true;

    private static final Boolean DEFAULT_IS_MODIFIED_BY_LABORATORY = false;
    private static final Boolean UPDATED_IS_MODIFIED_BY_LABORATORY = true;

    private static final Boolean DEFAULT_IS_MODIFIED_BY_COURRIER = false;
    private static final Boolean UPDATED_IS_MODIFIED_BY_COURRIER = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/samples";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSampleMockMvc;

    private Sample sample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sample createEntity(EntityManager em) {
        Sample sample = new Sample()
            .appId(DEFAULT_APP_ID)
            .clientSampleId(DEFAULT_CLIENT_SAMPLE_ID)
            .clientPatientId(DEFAULT_CLIENT_PATIENT_ID)
            .labId(DEFAULT_LAB_ID)
            .clientId(DEFAULT_CLIENT_ID)
            .sampleType(DEFAULT_SAMPLE_TYPE)
            .testId(DEFAULT_TEST_ID)
            .dateCollected(DEFAULT_DATE_COLLECTED)
            .collectedBy(DEFAULT_COLLECTED_BY)
            .status(DEFAULT_STATUS)
            .comment(DEFAULT_COMMENT)
            .synced(DEFAULT_SYNCED)
            .dateSynced(DEFAULT_DATE_SYNCED)
            .labReferenceId(DEFAULT_LAB_REFERENCE_ID)
            .location(DEFAULT_LOCATION)
            .result(DEFAULT_RESULT)
            .resultReceivedBy(DEFAULT_RESULT_RECEIVED_BY)
            .shipmentId(DEFAULT_SHIPMENT_ID)
            .clientContact(DEFAULT_CLIENT_CONTACT)
            .temperatureAtHub(DEFAULT_TEMPERATURE_AT_HUB)
            .temperatureAtLab(DEFAULT_TEMPERATURE_AT_LAB)
            .isModifiedByHub(DEFAULT_IS_MODIFIED_BY_HUB)
            .isModifiedByFacility(DEFAULT_IS_MODIFIED_BY_FACILITY)
            .isModifiedByLaboratory(DEFAULT_IS_MODIFIED_BY_LABORATORY)
            .isModifiedByCourrier(DEFAULT_IS_MODIFIED_BY_COURRIER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sample;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sample createUpdatedEntity(EntityManager em) {
        Sample sample = new Sample()
            .appId(UPDATED_APP_ID)
            .clientSampleId(UPDATED_CLIENT_SAMPLE_ID)
            .clientPatientId(UPDATED_CLIENT_PATIENT_ID)
            .labId(UPDATED_LAB_ID)
            .clientId(UPDATED_CLIENT_ID)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .testId(UPDATED_TEST_ID)
            .dateCollected(UPDATED_DATE_COLLECTED)
            .collectedBy(UPDATED_COLLECTED_BY)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .synced(UPDATED_SYNCED)
            .dateSynced(UPDATED_DATE_SYNCED)
            .labReferenceId(UPDATED_LAB_REFERENCE_ID)
            .location(UPDATED_LOCATION)
            .result(UPDATED_RESULT)
            .resultReceivedBy(UPDATED_RESULT_RECEIVED_BY)
            .shipmentId(UPDATED_SHIPMENT_ID)
            .clientContact(UPDATED_CLIENT_CONTACT)
            .temperatureAtHub(UPDATED_TEMPERATURE_AT_HUB)
            .temperatureAtLab(UPDATED_TEMPERATURE_AT_LAB)
            .isModifiedByHub(UPDATED_IS_MODIFIED_BY_HUB)
            .isModifiedByFacility(UPDATED_IS_MODIFIED_BY_FACILITY)
            .isModifiedByLaboratory(UPDATED_IS_MODIFIED_BY_LABORATORY)
            .isModifiedByCourrier(UPDATED_IS_MODIFIED_BY_COURRIER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sample;
    }

    @BeforeEach
    public void initTest() {
        sample = createEntity(em);
    }

    @Test
    @Transactional
    void createSample() throws Exception {
        int databaseSizeBeforeCreate = sampleRepository.findAll().size();
        // Create the Sample
        restSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isCreated());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate + 1);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getAppId()).isEqualTo(DEFAULT_APP_ID);
        assertThat(testSample.getClientSampleId()).isEqualTo(DEFAULT_CLIENT_SAMPLE_ID);
        assertThat(testSample.getClientPatientId()).isEqualTo(DEFAULT_CLIENT_PATIENT_ID);
        assertThat(testSample.getLabId()).isEqualTo(DEFAULT_LAB_ID);
        assertThat(testSample.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testSample.getSampleType()).isEqualTo(DEFAULT_SAMPLE_TYPE);
        assertThat(testSample.getTestId()).isEqualTo(DEFAULT_TEST_ID);
        assertThat(testSample.getDateCollected()).isEqualTo(DEFAULT_DATE_COLLECTED);
        assertThat(testSample.getCollectedBy()).isEqualTo(DEFAULT_COLLECTED_BY);
        assertThat(testSample.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSample.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSample.getSynced()).isEqualTo(DEFAULT_SYNCED);
        assertThat(testSample.getDateSynced()).isEqualTo(DEFAULT_DATE_SYNCED);
        assertThat(testSample.getLabReferenceId()).isEqualTo(DEFAULT_LAB_REFERENCE_ID);
        assertThat(testSample.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSample.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testSample.getResultReceivedBy()).isEqualTo(DEFAULT_RESULT_RECEIVED_BY);
        assertThat(testSample.getShipmentId()).isEqualTo(DEFAULT_SHIPMENT_ID);
        assertThat(testSample.getClientContact()).isEqualTo(DEFAULT_CLIENT_CONTACT);
        assertThat(testSample.getTemperatureAtHub()).isEqualTo(DEFAULT_TEMPERATURE_AT_HUB);
        assertThat(testSample.getTemperatureAtLab()).isEqualTo(DEFAULT_TEMPERATURE_AT_LAB);
        assertThat(testSample.getIsModifiedByHub()).isEqualTo(DEFAULT_IS_MODIFIED_BY_HUB);
        assertThat(testSample.getIsModifiedByFacility()).isEqualTo(DEFAULT_IS_MODIFIED_BY_FACILITY);
        assertThat(testSample.getIsModifiedByLaboratory()).isEqualTo(DEFAULT_IS_MODIFIED_BY_LABORATORY);
        assertThat(testSample.getIsModifiedByCourrier()).isEqualTo(DEFAULT_IS_MODIFIED_BY_COURRIER);
        assertThat(testSample.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSample.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSample.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSample.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSampleWithExistingId() throws Exception {
        // Create the Sample with an existing ID
        sample.setId(1L);

        int databaseSizeBeforeCreate = sampleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSamples() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get all the sampleList
        restSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sample.getId().intValue())))
            .andExpect(jsonPath("$.[*].appId").value(hasItem(DEFAULT_APP_ID)))
            .andExpect(jsonPath("$.[*].clientSampleId").value(hasItem(DEFAULT_CLIENT_SAMPLE_ID)))
            .andExpect(jsonPath("$.[*].clientPatientId").value(hasItem(DEFAULT_CLIENT_PATIENT_ID)))
            .andExpect(jsonPath("$.[*].labId").value(hasItem(DEFAULT_LAB_ID)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].sampleType").value(hasItem(DEFAULT_SAMPLE_TYPE)))
            .andExpect(jsonPath("$.[*].testId").value(hasItem(DEFAULT_TEST_ID)))
            .andExpect(jsonPath("$.[*].dateCollected").value(hasItem(DEFAULT_DATE_COLLECTED.toString())))
            .andExpect(jsonPath("$.[*].collectedBy").value(hasItem(DEFAULT_COLLECTED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].synced").value(hasItem(DEFAULT_SYNCED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateSynced").value(hasItem(DEFAULT_DATE_SYNCED.toString())))
            .andExpect(jsonPath("$.[*].labReferenceId").value(hasItem(DEFAULT_LAB_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].resultReceivedBy").value(hasItem(DEFAULT_RESULT_RECEIVED_BY)))
            .andExpect(jsonPath("$.[*].shipmentId").value(hasItem(DEFAULT_SHIPMENT_ID)))
            .andExpect(jsonPath("$.[*].clientContact").value(hasItem(DEFAULT_CLIENT_CONTACT)))
            .andExpect(jsonPath("$.[*].temperatureAtHub").value(hasItem(DEFAULT_TEMPERATURE_AT_HUB)))
            .andExpect(jsonPath("$.[*].temperatureAtLab").value(hasItem(DEFAULT_TEMPERATURE_AT_LAB)))
            .andExpect(jsonPath("$.[*].isModifiedByHub").value(hasItem(DEFAULT_IS_MODIFIED_BY_HUB.booleanValue())))
            .andExpect(jsonPath("$.[*].isModifiedByFacility").value(hasItem(DEFAULT_IS_MODIFIED_BY_FACILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].isModifiedByLaboratory").value(hasItem(DEFAULT_IS_MODIFIED_BY_LABORATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].isModifiedByCourrier").value(hasItem(DEFAULT_IS_MODIFIED_BY_COURRIER.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get the sample
        restSampleMockMvc
            .perform(get(ENTITY_API_URL_ID, sample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sample.getId().intValue()))
            .andExpect(jsonPath("$.appId").value(DEFAULT_APP_ID))
            .andExpect(jsonPath("$.clientSampleId").value(DEFAULT_CLIENT_SAMPLE_ID))
            .andExpect(jsonPath("$.clientPatientId").value(DEFAULT_CLIENT_PATIENT_ID))
            .andExpect(jsonPath("$.labId").value(DEFAULT_LAB_ID))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.sampleType").value(DEFAULT_SAMPLE_TYPE))
            .andExpect(jsonPath("$.testId").value(DEFAULT_TEST_ID))
            .andExpect(jsonPath("$.dateCollected").value(DEFAULT_DATE_COLLECTED.toString()))
            .andExpect(jsonPath("$.collectedBy").value(DEFAULT_COLLECTED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.synced").value(DEFAULT_SYNCED.booleanValue()))
            .andExpect(jsonPath("$.dateSynced").value(DEFAULT_DATE_SYNCED.toString()))
            .andExpect(jsonPath("$.labReferenceId").value(DEFAULT_LAB_REFERENCE_ID))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.resultReceivedBy").value(DEFAULT_RESULT_RECEIVED_BY))
            .andExpect(jsonPath("$.shipmentId").value(DEFAULT_SHIPMENT_ID))
            .andExpect(jsonPath("$.clientContact").value(DEFAULT_CLIENT_CONTACT))
            .andExpect(jsonPath("$.temperatureAtHub").value(DEFAULT_TEMPERATURE_AT_HUB))
            .andExpect(jsonPath("$.temperatureAtLab").value(DEFAULT_TEMPERATURE_AT_LAB))
            .andExpect(jsonPath("$.isModifiedByHub").value(DEFAULT_IS_MODIFIED_BY_HUB.booleanValue()))
            .andExpect(jsonPath("$.isModifiedByFacility").value(DEFAULT_IS_MODIFIED_BY_FACILITY.booleanValue()))
            .andExpect(jsonPath("$.isModifiedByLaboratory").value(DEFAULT_IS_MODIFIED_BY_LABORATORY.booleanValue()))
            .andExpect(jsonPath("$.isModifiedByCourrier").value(DEFAULT_IS_MODIFIED_BY_COURRIER.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSample() throws Exception {
        // Get the sample
        restSampleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample
        Sample updatedSample = sampleRepository.findById(sample.getId()).get();
        // Disconnect from session so that the updates on updatedSample are not directly saved in db
        em.detach(updatedSample);
        updatedSample
            .appId(UPDATED_APP_ID)
            .clientSampleId(UPDATED_CLIENT_SAMPLE_ID)
            .clientPatientId(UPDATED_CLIENT_PATIENT_ID)
            .labId(UPDATED_LAB_ID)
            .clientId(UPDATED_CLIENT_ID)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .testId(UPDATED_TEST_ID)
            .dateCollected(UPDATED_DATE_COLLECTED)
            .collectedBy(UPDATED_COLLECTED_BY)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .synced(UPDATED_SYNCED)
            .dateSynced(UPDATED_DATE_SYNCED)
            .labReferenceId(UPDATED_LAB_REFERENCE_ID)
            .location(UPDATED_LOCATION)
            .result(UPDATED_RESULT)
            .resultReceivedBy(UPDATED_RESULT_RECEIVED_BY)
            .shipmentId(UPDATED_SHIPMENT_ID)
            .clientContact(UPDATED_CLIENT_CONTACT)
            .temperatureAtHub(UPDATED_TEMPERATURE_AT_HUB)
            .temperatureAtLab(UPDATED_TEMPERATURE_AT_LAB)
            .isModifiedByHub(UPDATED_IS_MODIFIED_BY_HUB)
            .isModifiedByFacility(UPDATED_IS_MODIFIED_BY_FACILITY)
            .isModifiedByLaboratory(UPDATED_IS_MODIFIED_BY_LABORATORY)
            .isModifiedByCourrier(UPDATED_IS_MODIFIED_BY_COURRIER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSample))
            )
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testSample.getClientSampleId()).isEqualTo(UPDATED_CLIENT_SAMPLE_ID);
        assertThat(testSample.getClientPatientId()).isEqualTo(UPDATED_CLIENT_PATIENT_ID);
        assertThat(testSample.getLabId()).isEqualTo(UPDATED_LAB_ID);
        assertThat(testSample.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testSample.getSampleType()).isEqualTo(UPDATED_SAMPLE_TYPE);
        assertThat(testSample.getTestId()).isEqualTo(UPDATED_TEST_ID);
        assertThat(testSample.getDateCollected()).isEqualTo(UPDATED_DATE_COLLECTED);
        assertThat(testSample.getCollectedBy()).isEqualTo(UPDATED_COLLECTED_BY);
        assertThat(testSample.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSample.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSample.getSynced()).isEqualTo(UPDATED_SYNCED);
        assertThat(testSample.getDateSynced()).isEqualTo(UPDATED_DATE_SYNCED);
        assertThat(testSample.getLabReferenceId()).isEqualTo(UPDATED_LAB_REFERENCE_ID);
        assertThat(testSample.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSample.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testSample.getResultReceivedBy()).isEqualTo(UPDATED_RESULT_RECEIVED_BY);
        assertThat(testSample.getShipmentId()).isEqualTo(UPDATED_SHIPMENT_ID);
        assertThat(testSample.getClientContact()).isEqualTo(UPDATED_CLIENT_CONTACT);
        assertThat(testSample.getTemperatureAtHub()).isEqualTo(UPDATED_TEMPERATURE_AT_HUB);
        assertThat(testSample.getTemperatureAtLab()).isEqualTo(UPDATED_TEMPERATURE_AT_LAB);
        assertThat(testSample.getIsModifiedByHub()).isEqualTo(UPDATED_IS_MODIFIED_BY_HUB);
        assertThat(testSample.getIsModifiedByFacility()).isEqualTo(UPDATED_IS_MODIFIED_BY_FACILITY);
        assertThat(testSample.getIsModifiedByLaboratory()).isEqualTo(UPDATED_IS_MODIFIED_BY_LABORATORY);
        assertThat(testSample.getIsModifiedByCourrier()).isEqualTo(UPDATED_IS_MODIFIED_BY_COURRIER);
        assertThat(testSample.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSample.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSample.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSample.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSampleWithPatch() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample using partial update
        Sample partialUpdatedSample = new Sample();
        partialUpdatedSample.setId(sample.getId());

        partialUpdatedSample
            .clientPatientId(UPDATED_CLIENT_PATIENT_ID)
            .labId(UPDATED_LAB_ID)
            .clientId(UPDATED_CLIENT_ID)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .dateSynced(UPDATED_DATE_SYNCED)
            .labReferenceId(UPDATED_LAB_REFERENCE_ID)
            .location(UPDATED_LOCATION)
            .clientContact(UPDATED_CLIENT_CONTACT)
            .temperatureAtHub(UPDATED_TEMPERATURE_AT_HUB)
            .isModifiedByLaboratory(UPDATED_IS_MODIFIED_BY_LABORATORY)
            .isModifiedByCourrier(UPDATED_IS_MODIFIED_BY_COURRIER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSample))
            )
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getAppId()).isEqualTo(DEFAULT_APP_ID);
        assertThat(testSample.getClientSampleId()).isEqualTo(DEFAULT_CLIENT_SAMPLE_ID);
        assertThat(testSample.getClientPatientId()).isEqualTo(UPDATED_CLIENT_PATIENT_ID);
        assertThat(testSample.getLabId()).isEqualTo(UPDATED_LAB_ID);
        assertThat(testSample.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testSample.getSampleType()).isEqualTo(UPDATED_SAMPLE_TYPE);
        assertThat(testSample.getTestId()).isEqualTo(DEFAULT_TEST_ID);
        assertThat(testSample.getDateCollected()).isEqualTo(DEFAULT_DATE_COLLECTED);
        assertThat(testSample.getCollectedBy()).isEqualTo(DEFAULT_COLLECTED_BY);
        assertThat(testSample.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSample.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSample.getSynced()).isEqualTo(DEFAULT_SYNCED);
        assertThat(testSample.getDateSynced()).isEqualTo(UPDATED_DATE_SYNCED);
        assertThat(testSample.getLabReferenceId()).isEqualTo(UPDATED_LAB_REFERENCE_ID);
        assertThat(testSample.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSample.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testSample.getResultReceivedBy()).isEqualTo(DEFAULT_RESULT_RECEIVED_BY);
        assertThat(testSample.getShipmentId()).isEqualTo(DEFAULT_SHIPMENT_ID);
        assertThat(testSample.getClientContact()).isEqualTo(UPDATED_CLIENT_CONTACT);
        assertThat(testSample.getTemperatureAtHub()).isEqualTo(UPDATED_TEMPERATURE_AT_HUB);
        assertThat(testSample.getTemperatureAtLab()).isEqualTo(DEFAULT_TEMPERATURE_AT_LAB);
        assertThat(testSample.getIsModifiedByHub()).isEqualTo(DEFAULT_IS_MODIFIED_BY_HUB);
        assertThat(testSample.getIsModifiedByFacility()).isEqualTo(DEFAULT_IS_MODIFIED_BY_FACILITY);
        assertThat(testSample.getIsModifiedByLaboratory()).isEqualTo(UPDATED_IS_MODIFIED_BY_LABORATORY);
        assertThat(testSample.getIsModifiedByCourrier()).isEqualTo(UPDATED_IS_MODIFIED_BY_COURRIER);
        assertThat(testSample.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSample.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSample.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSample.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSampleWithPatch() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample using partial update
        Sample partialUpdatedSample = new Sample();
        partialUpdatedSample.setId(sample.getId());

        partialUpdatedSample
            .appId(UPDATED_APP_ID)
            .clientSampleId(UPDATED_CLIENT_SAMPLE_ID)
            .clientPatientId(UPDATED_CLIENT_PATIENT_ID)
            .labId(UPDATED_LAB_ID)
            .clientId(UPDATED_CLIENT_ID)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .testId(UPDATED_TEST_ID)
            .dateCollected(UPDATED_DATE_COLLECTED)
            .collectedBy(UPDATED_COLLECTED_BY)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .synced(UPDATED_SYNCED)
            .dateSynced(UPDATED_DATE_SYNCED)
            .labReferenceId(UPDATED_LAB_REFERENCE_ID)
            .location(UPDATED_LOCATION)
            .result(UPDATED_RESULT)
            .resultReceivedBy(UPDATED_RESULT_RECEIVED_BY)
            .shipmentId(UPDATED_SHIPMENT_ID)
            .clientContact(UPDATED_CLIENT_CONTACT)
            .temperatureAtHub(UPDATED_TEMPERATURE_AT_HUB)
            .temperatureAtLab(UPDATED_TEMPERATURE_AT_LAB)
            .isModifiedByHub(UPDATED_IS_MODIFIED_BY_HUB)
            .isModifiedByFacility(UPDATED_IS_MODIFIED_BY_FACILITY)
            .isModifiedByLaboratory(UPDATED_IS_MODIFIED_BY_LABORATORY)
            .isModifiedByCourrier(UPDATED_IS_MODIFIED_BY_COURRIER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSample))
            )
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testSample.getClientSampleId()).isEqualTo(UPDATED_CLIENT_SAMPLE_ID);
        assertThat(testSample.getClientPatientId()).isEqualTo(UPDATED_CLIENT_PATIENT_ID);
        assertThat(testSample.getLabId()).isEqualTo(UPDATED_LAB_ID);
        assertThat(testSample.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testSample.getSampleType()).isEqualTo(UPDATED_SAMPLE_TYPE);
        assertThat(testSample.getTestId()).isEqualTo(UPDATED_TEST_ID);
        assertThat(testSample.getDateCollected()).isEqualTo(UPDATED_DATE_COLLECTED);
        assertThat(testSample.getCollectedBy()).isEqualTo(UPDATED_COLLECTED_BY);
        assertThat(testSample.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSample.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSample.getSynced()).isEqualTo(UPDATED_SYNCED);
        assertThat(testSample.getDateSynced()).isEqualTo(UPDATED_DATE_SYNCED);
        assertThat(testSample.getLabReferenceId()).isEqualTo(UPDATED_LAB_REFERENCE_ID);
        assertThat(testSample.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSample.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testSample.getResultReceivedBy()).isEqualTo(UPDATED_RESULT_RECEIVED_BY);
        assertThat(testSample.getShipmentId()).isEqualTo(UPDATED_SHIPMENT_ID);
        assertThat(testSample.getClientContact()).isEqualTo(UPDATED_CLIENT_CONTACT);
        assertThat(testSample.getTemperatureAtHub()).isEqualTo(UPDATED_TEMPERATURE_AT_HUB);
        assertThat(testSample.getTemperatureAtLab()).isEqualTo(UPDATED_TEMPERATURE_AT_LAB);
        assertThat(testSample.getIsModifiedByHub()).isEqualTo(UPDATED_IS_MODIFIED_BY_HUB);
        assertThat(testSample.getIsModifiedByFacility()).isEqualTo(UPDATED_IS_MODIFIED_BY_FACILITY);
        assertThat(testSample.getIsModifiedByLaboratory()).isEqualTo(UPDATED_IS_MODIFIED_BY_LABORATORY);
        assertThat(testSample.getIsModifiedByCourrier()).isEqualTo(UPDATED_IS_MODIFIED_BY_COURRIER);
        assertThat(testSample.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSample.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSample.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSample.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sample))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();
        sample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeDelete = sampleRepository.findAll().size();

        // Delete the sample
        restSampleMockMvc
            .perform(delete(ENTITY_API_URL_ID, sample.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

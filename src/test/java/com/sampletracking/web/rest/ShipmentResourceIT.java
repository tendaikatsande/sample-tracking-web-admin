package com.sampletracking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sampletracking.IntegrationTest;
import com.sampletracking.domain.Shipment;
import com.sampletracking.repository.ShipmentRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ShipmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipmentResourceIT {

    private static final String DEFAULT_APP_ID = "AAAAAAAAAA";
    private static final String UPDATED_APP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SAMPLES = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLES = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_CREATED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CREATED = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RIDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_RIDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RIDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RIDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_CLUSTER_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLUSTER_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/shipments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentMockMvc;

    private Shipment shipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createEntity(EntityManager em) {
        Shipment shipment = new Shipment()
            .appId(DEFAULT_APP_ID)
            .description(DEFAULT_DESCRIPTION)
            .clientId(DEFAULT_CLIENT_ID)
            .samples(DEFAULT_SAMPLES)
            .status(DEFAULT_STATUS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .riderId(DEFAULT_RIDER_ID)
            .riderName(DEFAULT_RIDER_NAME)
            .destination(DEFAULT_DESTINATION)
            .clusterClientId(DEFAULT_CLUSTER_CLIENT_ID)
            .temperatureOrigin(DEFAULT_TEMPERATURE_ORIGIN)
            .temperatureDestination(DEFAULT_TEMPERATURE_DESTINATION)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return shipment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createUpdatedEntity(EntityManager em) {
        Shipment shipment = new Shipment()
            .appId(UPDATED_APP_ID)
            .description(UPDATED_DESCRIPTION)
            .clientId(UPDATED_CLIENT_ID)
            .samples(UPDATED_SAMPLES)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .riderId(UPDATED_RIDER_ID)
            .riderName(UPDATED_RIDER_NAME)
            .destination(UPDATED_DESTINATION)
            .clusterClientId(UPDATED_CLUSTER_CLIENT_ID)
            .temperatureOrigin(UPDATED_TEMPERATURE_ORIGIN)
            .temperatureDestination(UPDATED_TEMPERATURE_DESTINATION)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return shipment;
    }

    @BeforeEach
    public void initTest() {
        shipment = createEntity(em);
    }

    @Test
    @Transactional
    void createShipment() throws Exception {
        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();
        // Create the Shipment
        restShipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getAppId()).isEqualTo(DEFAULT_APP_ID);
        assertThat(testShipment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testShipment.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testShipment.getSamples()).isEqualTo(DEFAULT_SAMPLES);
        assertThat(testShipment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShipment.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testShipment.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testShipment.getRiderId()).isEqualTo(DEFAULT_RIDER_ID);
        assertThat(testShipment.getRiderName()).isEqualTo(DEFAULT_RIDER_NAME);
        assertThat(testShipment.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testShipment.getClusterClientId()).isEqualTo(DEFAULT_CLUSTER_CLIENT_ID);
        assertThat(testShipment.getTemperatureOrigin()).isEqualTo(DEFAULT_TEMPERATURE_ORIGIN);
        assertThat(testShipment.getTemperatureDestination()).isEqualTo(DEFAULT_TEMPERATURE_DESTINATION);
        assertThat(testShipment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testShipment.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createShipmentWithExistingId() throws Exception {
        // Create the Shipment with an existing ID
        shipment.setId("existing_id");

        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllShipments() throws Exception {
        // Initialize the database
        shipment.setId(UUID.randomUUID().toString());
        shipmentRepository.saveAndFlush(shipment);

        // Get all the shipmentList
        restShipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId())))
            .andExpect(jsonPath("$.[*].appId").value(hasItem(DEFAULT_APP_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].samples").value(hasItem(DEFAULT_SAMPLES.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].riderId").value(hasItem(DEFAULT_RIDER_ID)))
            .andExpect(jsonPath("$.[*].riderName").value(hasItem(DEFAULT_RIDER_NAME)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].clusterClientId").value(hasItem(DEFAULT_CLUSTER_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].temperatureOrigin").value(hasItem(DEFAULT_TEMPERATURE_ORIGIN)))
            .andExpect(jsonPath("$.[*].temperatureDestination").value(hasItem(DEFAULT_TEMPERATURE_DESTINATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    void getShipment() throws Exception {
        // Initialize the database
        shipment.setId(UUID.randomUUID().toString());
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipment.getId()))
            .andExpect(jsonPath("$.appId").value(DEFAULT_APP_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.samples").value(DEFAULT_SAMPLES.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.riderId").value(DEFAULT_RIDER_ID))
            .andExpect(jsonPath("$.riderName").value(DEFAULT_RIDER_NAME))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.clusterClientId").value(DEFAULT_CLUSTER_CLIENT_ID))
            .andExpect(jsonPath("$.temperatureOrigin").value(DEFAULT_TEMPERATURE_ORIGIN))
            .andExpect(jsonPath("$.temperatureDestination").value(DEFAULT_TEMPERATURE_DESTINATION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingShipment() throws Exception {
        // Get the shipment
        restShipmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShipment() throws Exception {
        // Initialize the database
        shipment.setId(UUID.randomUUID().toString());
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment
        Shipment updatedShipment = shipmentRepository.findById(shipment.getId()).get();
        // Disconnect from session so that the updates on updatedShipment are not directly saved in db
        em.detach(updatedShipment);
        updatedShipment
            .appId(UPDATED_APP_ID)
            .description(UPDATED_DESCRIPTION)
            .clientId(UPDATED_CLIENT_ID)
            .samples(UPDATED_SAMPLES)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .riderId(UPDATED_RIDER_ID)
            .riderName(UPDATED_RIDER_NAME)
            .destination(UPDATED_DESTINATION)
            .clusterClientId(UPDATED_CLUSTER_CLIENT_ID)
            .temperatureOrigin(UPDATED_TEMPERATURE_ORIGIN)
            .temperatureDestination(UPDATED_TEMPERATURE_DESTINATION)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedShipment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testShipment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShipment.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testShipment.getSamples()).isEqualTo(UPDATED_SAMPLES);
        assertThat(testShipment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShipment.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testShipment.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testShipment.getRiderId()).isEqualTo(UPDATED_RIDER_ID);
        assertThat(testShipment.getRiderName()).isEqualTo(UPDATED_RIDER_NAME);
        assertThat(testShipment.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testShipment.getClusterClientId()).isEqualTo(UPDATED_CLUSTER_CLIENT_ID);
        assertThat(testShipment.getTemperatureOrigin()).isEqualTo(UPDATED_TEMPERATURE_ORIGIN);
        assertThat(testShipment.getTemperatureDestination()).isEqualTo(UPDATED_TEMPERATURE_DESTINATION);
        assertThat(testShipment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testShipment.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipmentWithPatch() throws Exception {
        // Initialize the database
        shipment.setId(UUID.randomUUID().toString());
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment using partial update
        Shipment partialUpdatedShipment = new Shipment();
        partialUpdatedShipment.setId(shipment.getId());

        partialUpdatedShipment
            .appId(UPDATED_APP_ID)
            .description(UPDATED_DESCRIPTION)
            .samples(UPDATED_SAMPLES)
            .dateCreated(UPDATED_DATE_CREATED)
            .clusterClientId(UPDATED_CLUSTER_CLIENT_ID)
            .temperatureOrigin(UPDATED_TEMPERATURE_ORIGIN)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testShipment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShipment.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testShipment.getSamples()).isEqualTo(UPDATED_SAMPLES);
        assertThat(testShipment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShipment.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testShipment.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testShipment.getRiderId()).isEqualTo(DEFAULT_RIDER_ID);
        assertThat(testShipment.getRiderName()).isEqualTo(DEFAULT_RIDER_NAME);
        assertThat(testShipment.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testShipment.getClusterClientId()).isEqualTo(UPDATED_CLUSTER_CLIENT_ID);
        assertThat(testShipment.getTemperatureOrigin()).isEqualTo(UPDATED_TEMPERATURE_ORIGIN);
        assertThat(testShipment.getTemperatureDestination()).isEqualTo(DEFAULT_TEMPERATURE_DESTINATION);
        assertThat(testShipment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testShipment.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateShipmentWithPatch() throws Exception {
        // Initialize the database
        shipment.setId(UUID.randomUUID().toString());
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment using partial update
        Shipment partialUpdatedShipment = new Shipment();
        partialUpdatedShipment.setId(shipment.getId());

        partialUpdatedShipment
            .appId(UPDATED_APP_ID)
            .description(UPDATED_DESCRIPTION)
            .clientId(UPDATED_CLIENT_ID)
            .samples(UPDATED_SAMPLES)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .riderId(UPDATED_RIDER_ID)
            .riderName(UPDATED_RIDER_NAME)
            .destination(UPDATED_DESTINATION)
            .clusterClientId(UPDATED_CLUSTER_CLIENT_ID)
            .temperatureOrigin(UPDATED_TEMPERATURE_ORIGIN)
            .temperatureDestination(UPDATED_TEMPERATURE_DESTINATION)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getAppId()).isEqualTo(UPDATED_APP_ID);
        assertThat(testShipment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShipment.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testShipment.getSamples()).isEqualTo(UPDATED_SAMPLES);
        assertThat(testShipment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShipment.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testShipment.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testShipment.getRiderId()).isEqualTo(UPDATED_RIDER_ID);
        assertThat(testShipment.getRiderName()).isEqualTo(UPDATED_RIDER_NAME);
        assertThat(testShipment.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testShipment.getClusterClientId()).isEqualTo(UPDATED_CLUSTER_CLIENT_ID);
        assertThat(testShipment.getTemperatureOrigin()).isEqualTo(UPDATED_TEMPERATURE_ORIGIN);
        assertThat(testShipment.getTemperatureDestination()).isEqualTo(UPDATED_TEMPERATURE_DESTINATION);
        assertThat(testShipment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testShipment.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShipment() throws Exception {
        // Initialize the database
        shipment.setId(UUID.randomUUID().toString());
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeDelete = shipmentRepository.findAll().size();

        // Delete the shipment
        restShipmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
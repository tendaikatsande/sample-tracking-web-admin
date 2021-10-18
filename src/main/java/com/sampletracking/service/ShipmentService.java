package com.sampletracking.service;

import com.sampletracking.domain.Shipment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Shipment}.
 */
public interface ShipmentService {
    /**
     * Save a shipment.
     *
     * @param shipment the entity to save.
     * @return the persisted entity.
     */
    Shipment save(Shipment shipment);

    /**
     * Partially updates a shipment.
     *
     * @param shipment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Shipment> partialUpdate(Shipment shipment);

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Shipment> findAll(Pageable pageable);

    /**
     * Get the "id" shipment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Shipment> findOne(Long id);

    /**
     * Delete the "id" shipment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

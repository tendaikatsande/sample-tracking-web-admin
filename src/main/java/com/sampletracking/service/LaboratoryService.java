package com.sampletracking.service;

import com.sampletracking.domain.Laboratory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Laboratory}.
 */
public interface LaboratoryService {
    /**
     * Save a laboratory.
     *
     * @param laboratory the entity to save.
     * @return the persisted entity.
     */
    Laboratory save(Laboratory laboratory);

    /**
     * Partially updates a laboratory.
     *
     * @param laboratory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Laboratory> partialUpdate(Laboratory laboratory);

    /**
     * Get all the laboratories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Laboratory> findAll(Pageable pageable);

    /**
     * Get the "id" laboratory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Laboratory> findOne(String id);

    /**
     * Delete the "id" laboratory.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

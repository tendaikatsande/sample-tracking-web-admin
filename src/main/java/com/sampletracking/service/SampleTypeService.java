package com.sampletracking.service;

import com.sampletracking.domain.SampleType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SampleType}.
 */
public interface SampleTypeService {
    /**
     * Save a sampleType.
     *
     * @param sampleType the entity to save.
     * @return the persisted entity.
     */
    SampleType save(SampleType sampleType);

    /**
     * Partially updates a sampleType.
     *
     * @param sampleType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SampleType> partialUpdate(SampleType sampleType);

    /**
     * Get all the sampleTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SampleType> findAll(Pageable pageable);

    /**
     * Get the "id" sampleType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SampleType> findOne(String id);

    /**
     * Delete the "id" sampleType.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

package com.sampletracking.service;

import com.sampletracking.domain.Sample;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Sample}.
 */
public interface SampleService {
    /**
     * Save a sample.
     *
     * @param sample the entity to save.
     * @return the persisted entity.
     */
    Sample save(Sample sample);

    /**
     * Partially updates a sample.
     *
     * @param sample the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sample> partialUpdate(Sample sample);

    /**
     * Get all the samples.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sample> findAll(Pageable pageable);

    /**
     * Get the "id" sample.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sample> findOne(String id);

    /**
     * Delete the "id" sample.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

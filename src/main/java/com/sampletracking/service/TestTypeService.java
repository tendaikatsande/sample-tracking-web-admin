package com.sampletracking.service;

import com.sampletracking.domain.TestType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TestType}.
 */
public interface TestTypeService {
    /**
     * Save a testType.
     *
     * @param testType the entity to save.
     * @return the persisted entity.
     */
    TestType save(TestType testType);

    /**
     * Partially updates a testType.
     *
     * @param testType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TestType> partialUpdate(TestType testType);

    /**
     * Get all the testTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestType> findAll(Pageable pageable);

    /**
     * Get the "id" testType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestType> findOne(Long id);

    /**
     * Delete the "id" testType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

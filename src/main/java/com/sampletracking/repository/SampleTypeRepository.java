package com.sampletracking.repository;

import com.sampletracking.domain.SampleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SampleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleTypeRepository extends JpaRepository<SampleType, Long> {}

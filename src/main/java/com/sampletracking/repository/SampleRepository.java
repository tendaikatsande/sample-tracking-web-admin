package com.sampletracking.repository;

import com.sampletracking.domain.Sample;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {}

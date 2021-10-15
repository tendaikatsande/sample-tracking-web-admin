package com.sampletracking.repository;

import com.sampletracking.domain.TestType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TestType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestTypeRepository extends JpaRepository<TestType, String> {}

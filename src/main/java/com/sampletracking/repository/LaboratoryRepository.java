package com.sampletracking.repository;

import com.sampletracking.domain.Laboratory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Laboratory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, String> {}

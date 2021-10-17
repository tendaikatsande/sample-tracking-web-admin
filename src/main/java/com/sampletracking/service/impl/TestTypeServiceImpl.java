package com.sampletracking.service.impl;

import com.sampletracking.domain.TestType;
import com.sampletracking.repository.TestTypeRepository;
import com.sampletracking.service.TestTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TestType}.
 */
@Service
@Transactional
public class TestTypeServiceImpl implements TestTypeService {

    private final Logger log = LoggerFactory.getLogger(TestTypeServiceImpl.class);

    private final TestTypeRepository testTypeRepository;

    public TestTypeServiceImpl(TestTypeRepository testTypeRepository) {
        this.testTypeRepository = testTypeRepository;
    }

    @Override
    public TestType save(TestType testType) {
        log.debug("Request to save TestType : {}", testType);
        return testTypeRepository.save(testType);
    }

    @Override
    public Optional<TestType> partialUpdate(TestType testType) {
        log.debug("Request to partially update TestType : {}", testType);

        return testTypeRepository
            .findById(testType.getId())
            .map(existingTestType -> {
                if (testType.getName() != null) {
                    existingTestType.setName(testType.getName());
                }
                if (testType.getPrefix() != null) {
                    existingTestType.setPrefix(testType.getPrefix());
                }
                if (testType.getCreatedBy() != null) {
                    existingTestType.setCreatedBy(testType.getCreatedBy());
                }
                if (testType.getModifiedBy() != null) {
                    existingTestType.setModifiedBy(testType.getModifiedBy());
                }
                if (testType.getDateCreated() != null) {
                    existingTestType.setDateCreated(testType.getDateCreated());
                }
                if (testType.getDateModified() != null) {
                    existingTestType.setDateModified(testType.getDateModified());
                }

                return existingTestType;
            })
            .map(testTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestType> findAll(Pageable pageable) {
        log.debug("Request to get all TestTypes");
        return testTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TestType> findOne(String id) {
        log.debug("Request to get TestType : {}", id);
        return testTypeRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete TestType : {}", id);
        testTypeRepository.deleteById(id);
    }
}

package com.sampletracking.service.impl;

import com.sampletracking.domain.SampleType;
import com.sampletracking.repository.SampleTypeRepository;
import com.sampletracking.service.SampleTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SampleType}.
 */
@Service
@Transactional
public class SampleTypeServiceImpl implements SampleTypeService {

    private final Logger log = LoggerFactory.getLogger(SampleTypeServiceImpl.class);

    private final SampleTypeRepository sampleTypeRepository;

    public SampleTypeServiceImpl(SampleTypeRepository sampleTypeRepository) {
        this.sampleTypeRepository = sampleTypeRepository;
    }

    @Override
    public SampleType save(SampleType sampleType) {
        log.debug("Request to save SampleType : {}", sampleType);
        return sampleTypeRepository.save(sampleType);
    }

    @Override
    public Optional<SampleType> partialUpdate(SampleType sampleType) {
        log.debug("Request to partially update SampleType : {}", sampleType);

        return sampleTypeRepository
            .findById(sampleType.getId())
            .map(existingSampleType -> {
                if (sampleType.getName() != null) {
                    existingSampleType.setName(sampleType.getName());
                }
                if (sampleType.getPrefix() != null) {
                    existingSampleType.setPrefix(sampleType.getPrefix());
                }
                if (sampleType.getCreatedBy() != null) {
                    existingSampleType.setCreatedBy(sampleType.getCreatedBy());
                }
                if (sampleType.getCreatedDate() != null) {
                    existingSampleType.setCreatedDate(sampleType.getCreatedDate());
                }
                if (sampleType.getLastModifiedBy() != null) {
                    existingSampleType.setLastModifiedBy(sampleType.getLastModifiedBy());
                }
                if (sampleType.getLastModifiedDate() != null) {
                    existingSampleType.setLastModifiedDate(sampleType.getLastModifiedDate());
                }

                return existingSampleType;
            })
            .map(sampleTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SampleType> findAll(Pageable pageable) {
        log.debug("Request to get all SampleTypes");
        return sampleTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SampleType> findOne(Long id) {
        log.debug("Request to get SampleType : {}", id);
        return sampleTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SampleType : {}", id);
        sampleTypeRepository.deleteById(id);
    }
}

package com.sampletracking.service.impl;

import com.sampletracking.domain.Laboratory;
import com.sampletracking.repository.LaboratoryRepository;
import com.sampletracking.service.LaboratoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Laboratory}.
 */
@Service
@Transactional
public class LaboratoryServiceImpl implements LaboratoryService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryServiceImpl.class);

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Laboratory save(Laboratory laboratory) {
        log.debug("Request to save Laboratory : {}", laboratory);
        return laboratoryRepository.save(laboratory);
    }

    @Override
    public Optional<Laboratory> partialUpdate(Laboratory laboratory) {
        log.debug("Request to partially update Laboratory : {}", laboratory);

        return laboratoryRepository
            .findById(laboratory.getId())
            .map(existingLaboratory -> {
                if (laboratory.getName() != null) {
                    existingLaboratory.setName(laboratory.getName());
                }
                if (laboratory.getType() != null) {
                    existingLaboratory.setType(laboratory.getType());
                }
                if (laboratory.getCode() != null) {
                    existingLaboratory.setCode(laboratory.getCode());
                }
                if (laboratory.getCreatedBy() != null) {
                    existingLaboratory.setCreatedBy(laboratory.getCreatedBy());
                }
                if (laboratory.getCreatedDate() != null) {
                    existingLaboratory.setCreatedDate(laboratory.getCreatedDate());
                }
                if (laboratory.getLastModifiedBy() != null) {
                    existingLaboratory.setLastModifiedBy(laboratory.getLastModifiedBy());
                }
                if (laboratory.getLastModifiedDate() != null) {
                    existingLaboratory.setLastModifiedDate(laboratory.getLastModifiedDate());
                }

                return existingLaboratory;
            })
            .map(laboratoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Laboratory> findAll(Pageable pageable) {
        log.debug("Request to get all Laboratories");
        return laboratoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratory> findOne(String id) {
        log.debug("Request to get Laboratory : {}", id);
        return laboratoryRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Laboratory : {}", id);
        laboratoryRepository.deleteById(id);
    }
}

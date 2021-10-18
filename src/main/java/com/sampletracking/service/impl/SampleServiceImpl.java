package com.sampletracking.service.impl;

import com.sampletracking.domain.Sample;
import com.sampletracking.repository.SampleRepository;
import com.sampletracking.service.SampleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sample}.
 */
@Service
@Transactional
public class SampleServiceImpl implements SampleService {

    private final Logger log = LoggerFactory.getLogger(SampleServiceImpl.class);

    private final SampleRepository sampleRepository;

    public SampleServiceImpl(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @Override
    public Sample save(Sample sample) {
        log.debug("Request to save Sample : {}", sample);
        return sampleRepository.save(sample);
    }

    @Override
    public Optional<Sample> partialUpdate(Sample sample) {
        log.debug("Request to partially update Sample : {}", sample);

        return sampleRepository
            .findById(sample.getId())
            .map(existingSample -> {
                if (sample.getAppId() != null) {
                    existingSample.setAppId(sample.getAppId());
                }
                if (sample.getClientSampleId() != null) {
                    existingSample.setClientSampleId(sample.getClientSampleId());
                }
                if (sample.getClientPatientId() != null) {
                    existingSample.setClientPatientId(sample.getClientPatientId());
                }
                if (sample.getLabId() != null) {
                    existingSample.setLabId(sample.getLabId());
                }
                if (sample.getClientId() != null) {
                    existingSample.setClientId(sample.getClientId());
                }
                if (sample.getSampleType() != null) {
                    existingSample.setSampleType(sample.getSampleType());
                }
                if (sample.getTestId() != null) {
                    existingSample.setTestId(sample.getTestId());
                }
                if (sample.getDateCollected() != null) {
                    existingSample.setDateCollected(sample.getDateCollected());
                }
                if (sample.getCollectedBy() != null) {
                    existingSample.setCollectedBy(sample.getCollectedBy());
                }
                if (sample.getStatus() != null) {
                    existingSample.setStatus(sample.getStatus());
                }
                if (sample.getComment() != null) {
                    existingSample.setComment(sample.getComment());
                }
                if (sample.getSynced() != null) {
                    existingSample.setSynced(sample.getSynced());
                }
                if (sample.getDateSynced() != null) {
                    existingSample.setDateSynced(sample.getDateSynced());
                }
                if (sample.getLabReferenceId() != null) {
                    existingSample.setLabReferenceId(sample.getLabReferenceId());
                }
                if (sample.getLocation() != null) {
                    existingSample.setLocation(sample.getLocation());
                }
                if (sample.getResult() != null) {
                    existingSample.setResult(sample.getResult());
                }
                if (sample.getResultReceivedBy() != null) {
                    existingSample.setResultReceivedBy(sample.getResultReceivedBy());
                }
                if (sample.getShipmentId() != null) {
                    existingSample.setShipmentId(sample.getShipmentId());
                }
                if (sample.getClientContact() != null) {
                    existingSample.setClientContact(sample.getClientContact());
                }
                if (sample.getTemperatureAtHub() != null) {
                    existingSample.setTemperatureAtHub(sample.getTemperatureAtHub());
                }
                if (sample.getTemperatureAtLab() != null) {
                    existingSample.setTemperatureAtLab(sample.getTemperatureAtLab());
                }
                if (sample.getIsModifiedByHub() != null) {
                    existingSample.setIsModifiedByHub(sample.getIsModifiedByHub());
                }
                if (sample.getIsModifiedByFacility() != null) {
                    existingSample.setIsModifiedByFacility(sample.getIsModifiedByFacility());
                }
                if (sample.getIsModifiedByLaboratory() != null) {
                    existingSample.setIsModifiedByLaboratory(sample.getIsModifiedByLaboratory());
                }
                if (sample.getIsModifiedByCourier() != null) {
                    existingSample.setIsModifiedByCourier(sample.getIsModifiedByCourier());
                }
                if (sample.getCreatedBy() != null) {
                    existingSample.setCreatedBy(sample.getCreatedBy());
                }
                if (sample.getCreatedDate() != null) {
                    existingSample.setCreatedDate(sample.getCreatedDate());
                }
                if (sample.getLastModifiedBy() != null) {
                    existingSample.setLastModifiedBy(sample.getLastModifiedBy());
                }
                if (sample.getLastModifiedDate() != null) {
                    existingSample.setLastModifiedDate(sample.getLastModifiedDate());
                }

                return existingSample;
            })
            .map(sampleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sample> findAll(Pageable pageable) {
        log.debug("Request to get all Samples");
        return sampleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sample> findOne(String id) {
        log.debug("Request to get Sample : {}", id);
        return sampleRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Sample : {}", id);
        sampleRepository.deleteById(id);
    }
}

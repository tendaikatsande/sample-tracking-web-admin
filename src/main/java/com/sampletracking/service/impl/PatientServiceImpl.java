package com.sampletracking.service.impl;

import com.sampletracking.domain.Patient;
import com.sampletracking.repository.PatientRepository;
import com.sampletracking.service.PatientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> partialUpdate(Patient patient) {
        log.debug("Request to partially update Patient : {}", patient);

        return patientRepository
            .findById(patient.getId())
            .map(existingPatient -> {
                if (patient.getAppId() != null) {
                    existingPatient.setAppId(patient.getAppId());
                }
                if (patient.getFirstName() != null) {
                    existingPatient.setFirstName(patient.getFirstName());
                }
                if (patient.getLastName() != null) {
                    existingPatient.setLastName(patient.getLastName());
                }
                if (patient.getGender() != null) {
                    existingPatient.setGender(patient.getGender());
                }
                if (patient.getDob() != null) {
                    existingPatient.setDob(patient.getDob());
                }
                if (patient.getClient() != null) {
                    existingPatient.setClient(patient.getClient());
                }
                if (patient.getClientPatientId() != null) {
                    existingPatient.setClientPatientId(patient.getClientPatientId());
                }
                if (patient.getCohortNumber() != null) {
                    existingPatient.setCohortNumber(patient.getCohortNumber());
                }
                if (patient.getDateCreated() != null) {
                    existingPatient.setDateCreated(patient.getDateCreated());
                }
                if (patient.getDateModified() != null) {
                    existingPatient.setDateModified(patient.getDateModified());
                }
                if (patient.getPhoneNumber() != null) {
                    existingPatient.setPhoneNumber(patient.getPhoneNumber());
                }
                if (patient.getCreatedBy() != null) {
                    existingPatient.setCreatedBy(patient.getCreatedBy());
                }
                if (patient.getModifiedBy() != null) {
                    existingPatient.setModifiedBy(patient.getModifiedBy());
                }

                return existingPatient;
            })
            .map(patientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Patient> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findOne(String id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}

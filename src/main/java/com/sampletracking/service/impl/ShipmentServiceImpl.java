package com.sampletracking.service.impl;

import com.sampletracking.domain.Shipment;
import com.sampletracking.repository.ShipmentRepository;
import com.sampletracking.service.ShipmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Shipment}.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        log.debug("Request to save Shipment : {}", shipment);
        return shipmentRepository.save(shipment);
    }

    @Override
    public Optional<Shipment> partialUpdate(Shipment shipment) {
        log.debug("Request to partially update Shipment : {}", shipment);

        return shipmentRepository
            .findById(shipment.getId())
            .map(existingShipment -> {
                if (shipment.getAppId() != null) {
                    existingShipment.setAppId(shipment.getAppId());
                }
                if (shipment.getDescription() != null) {
                    existingShipment.setDescription(shipment.getDescription());
                }
                if (shipment.getClientId() != null) {
                    existingShipment.setClientId(shipment.getClientId());
                }
                if (shipment.getSamples() != null) {
                    existingShipment.setSamples(shipment.getSamples());
                }
                if (shipment.getStatus() != null) {
                    existingShipment.setStatus(shipment.getStatus());
                }
                if (shipment.getDateCreated() != null) {
                    existingShipment.setDateCreated(shipment.getDateCreated());
                }
                if (shipment.getDateModified() != null) {
                    existingShipment.setDateModified(shipment.getDateModified());
                }
                if (shipment.getRiderId() != null) {
                    existingShipment.setRiderId(shipment.getRiderId());
                }
                if (shipment.getRiderName() != null) {
                    existingShipment.setRiderName(shipment.getRiderName());
                }
                if (shipment.getDestination() != null) {
                    existingShipment.setDestination(shipment.getDestination());
                }
                if (shipment.getClusterClientId() != null) {
                    existingShipment.setClusterClientId(shipment.getClusterClientId());
                }
                if (shipment.getTemperatureOrigin() != null) {
                    existingShipment.setTemperatureOrigin(shipment.getTemperatureOrigin());
                }
                if (shipment.getTemperatureDestination() != null) {
                    existingShipment.setTemperatureDestination(shipment.getTemperatureDestination());
                }
                if (shipment.getIsModifiedByHub() != null) {
                    existingShipment.setIsModifiedByHub(shipment.getIsModifiedByHub());
                }
                if (shipment.getIsModifiedByFacility() != null) {
                    existingShipment.setIsModifiedByFacility(shipment.getIsModifiedByFacility());
                }
                if (shipment.getIsModifiedByLaboratory() != null) {
                    existingShipment.setIsModifiedByLaboratory(shipment.getIsModifiedByLaboratory());
                }
                if (shipment.getIsModifiedByCourrier() != null) {
                    existingShipment.setIsModifiedByCourrier(shipment.getIsModifiedByCourrier());
                }
                if (shipment.getCreatedBy() != null) {
                    existingShipment.setCreatedBy(shipment.getCreatedBy());
                }
                if (shipment.getCreatedDate() != null) {
                    existingShipment.setCreatedDate(shipment.getCreatedDate());
                }
                if (shipment.getLastModifiedBy() != null) {
                    existingShipment.setLastModifiedBy(shipment.getLastModifiedBy());
                }
                if (shipment.getLastModifiedDate() != null) {
                    existingShipment.setLastModifiedDate(shipment.getLastModifiedDate());
                }

                return existingShipment;
            })
            .map(shipmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Shipment> findAll(Pageable pageable) {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findOne(String id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Shipment : {}", id);
        shipmentRepository.deleteById(id);
    }
}

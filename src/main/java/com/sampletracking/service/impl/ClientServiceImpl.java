package com.sampletracking.service.impl;

import com.sampletracking.domain.Client;
import com.sampletracking.repository.ClientRepository;
import com.sampletracking.service.ClientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getClientUid() != null) {
                    existingClient.setClientUid(client.getClientUid());
                }
                if (client.getClientId() != null) {
                    existingClient.setClientId(client.getClientId());
                }
                if (client.getName() != null) {
                    existingClient.setName(client.getName());
                }
                if (client.getPhone() != null) {
                    existingClient.setPhone(client.getPhone());
                }
                if (client.getDistrictId() != null) {
                    existingClient.setDistrictId(client.getDistrictId());
                }
                if (client.getDistrictName() != null) {
                    existingClient.setDistrictName(client.getDistrictName());
                }
                if (client.getProvinceId() != null) {
                    existingClient.setProvinceId(client.getProvinceId());
                }
                if (client.getProvinceName() != null) {
                    existingClient.setProvinceName(client.getProvinceName());
                }
                if (client.getCreatedBy() != null) {
                    existingClient.setCreatedBy(client.getCreatedBy());
                }
                if (client.getCreatedDate() != null) {
                    existingClient.setCreatedDate(client.getCreatedDate());
                }
                if (client.getLastModifiedBy() != null) {
                    existingClient.setLastModifiedBy(client.getLastModifiedBy());
                }
                if (client.getLastModifiedDate() != null) {
                    existingClient.setLastModifiedDate(client.getLastModifiedDate());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}

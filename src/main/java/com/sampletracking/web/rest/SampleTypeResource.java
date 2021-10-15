package com.sampletracking.web.rest;

import com.sampletracking.domain.SampleType;
import com.sampletracking.repository.SampleTypeRepository;
import com.sampletracking.service.SampleTypeService;
import com.sampletracking.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sampletracking.domain.SampleType}.
 */
@RestController
@RequestMapping("/api")
public class SampleTypeResource {

    private final Logger log = LoggerFactory.getLogger(SampleTypeResource.class);

    private static final String ENTITY_NAME = "sampleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SampleTypeService sampleTypeService;

    private final SampleTypeRepository sampleTypeRepository;

    public SampleTypeResource(SampleTypeService sampleTypeService, SampleTypeRepository sampleTypeRepository) {
        this.sampleTypeService = sampleTypeService;
        this.sampleTypeRepository = sampleTypeRepository;
    }

    /**
     * {@code POST  /sample-types} : Create a new sampleType.
     *
     * @param sampleType the sampleType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sampleType, or with status {@code 400 (Bad Request)} if the sampleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sample-types")
    public ResponseEntity<SampleType> createSampleType(@RequestBody SampleType sampleType) throws URISyntaxException {
        log.debug("REST request to save SampleType : {}", sampleType);
        if (sampleType.getId() != null) {
            throw new BadRequestAlertException("A new sampleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleType result = sampleTypeService.save(sampleType);
        return ResponseEntity
            .created(new URI("/api/sample-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sample-types/:id} : Updates an existing sampleType.
     *
     * @param id the id of the sampleType to save.
     * @param sampleType the sampleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleType,
     * or with status {@code 400 (Bad Request)} if the sampleType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sampleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sample-types/{id}")
    public ResponseEntity<SampleType> updateSampleType(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SampleType sampleType
    ) throws URISyntaxException {
        log.debug("REST request to update SampleType : {}, {}", id, sampleType);
        if (sampleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SampleType result = sampleTypeService.save(sampleType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleType.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /sample-types/:id} : Partial updates given fields of an existing sampleType, field will ignore if it is null
     *
     * @param id the id of the sampleType to save.
     * @param sampleType the sampleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleType,
     * or with status {@code 400 (Bad Request)} if the sampleType is not valid,
     * or with status {@code 404 (Not Found)} if the sampleType is not found,
     * or with status {@code 500 (Internal Server Error)} if the sampleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sample-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SampleType> partialUpdateSampleType(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SampleType sampleType
    ) throws URISyntaxException {
        log.debug("REST request to partial update SampleType partially : {}, {}", id, sampleType);
        if (sampleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SampleType> result = sampleTypeService.partialUpdate(sampleType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleType.getId())
        );
    }

    /**
     * {@code GET  /sample-types} : get all the sampleTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sampleTypes in body.
     */
    @GetMapping("/sample-types")
    public ResponseEntity<List<SampleType>> getAllSampleTypes(Pageable pageable) {
        log.debug("REST request to get a page of SampleTypes");
        Page<SampleType> page = sampleTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sample-types/:id} : get the "id" sampleType.
     *
     * @param id the id of the sampleType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sampleType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sample-types/{id}")
    public ResponseEntity<SampleType> getSampleType(@PathVariable String id) {
        log.debug("REST request to get SampleType : {}", id);
        Optional<SampleType> sampleType = sampleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sampleType);
    }

    /**
     * {@code DELETE  /sample-types/:id} : delete the "id" sampleType.
     *
     * @param id the id of the sampleType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sample-types/{id}")
    public ResponseEntity<Void> deleteSampleType(@PathVariable String id) {
        log.debug("REST request to delete SampleType : {}", id);
        sampleTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

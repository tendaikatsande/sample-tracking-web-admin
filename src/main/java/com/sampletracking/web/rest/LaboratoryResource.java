package com.sampletracking.web.rest;

import com.sampletracking.domain.Laboratory;
import com.sampletracking.repository.LaboratoryRepository;
import com.sampletracking.service.LaboratoryService;
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
 * REST controller for managing {@link com.sampletracking.domain.Laboratory}.
 */
@RestController
@RequestMapping("/api")
public class LaboratoryResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoryResource.class);

    private static final String ENTITY_NAME = "laboratory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoryService laboratoryService;

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryResource(LaboratoryService laboratoryService, LaboratoryRepository laboratoryRepository) {
        this.laboratoryService = laboratoryService;
        this.laboratoryRepository = laboratoryRepository;
    }

    /**
     * {@code POST  /laboratories} : Create a new laboratory.
     *
     * @param laboratory the laboratory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laboratory, or with status {@code 400 (Bad Request)} if the laboratory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laboratories")
    public ResponseEntity<Laboratory> createLaboratory(@RequestBody Laboratory laboratory) throws URISyntaxException {
        log.debug("REST request to save Laboratory : {}", laboratory);
        if (laboratory.getId() != null) {
            throw new BadRequestAlertException("A new laboratory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Laboratory result = laboratoryService.save(laboratory);
        return ResponseEntity
            .created(new URI("/api/laboratories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /laboratories/:id} : Updates an existing laboratory.
     *
     * @param id the id of the laboratory to save.
     * @param laboratory the laboratory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratory,
     * or with status {@code 400 (Bad Request)} if the laboratory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the laboratory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/laboratories/{id}")
    public ResponseEntity<Laboratory> updateLaboratory(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Laboratory laboratory
    ) throws URISyntaxException {
        log.debug("REST request to update Laboratory : {}, {}", id, laboratory);
        if (laboratory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Laboratory result = laboratoryService.save(laboratory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratory.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /laboratories/:id} : Partial updates given fields of an existing laboratory, field will ignore if it is null
     *
     * @param id the id of the laboratory to save.
     * @param laboratory the laboratory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratory,
     * or with status {@code 400 (Bad Request)} if the laboratory is not valid,
     * or with status {@code 404 (Not Found)} if the laboratory is not found,
     * or with status {@code 500 (Internal Server Error)} if the laboratory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/laboratories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Laboratory> partialUpdateLaboratory(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Laboratory laboratory
    ) throws URISyntaxException {
        log.debug("REST request to partial update Laboratory partially : {}, {}", id, laboratory);
        if (laboratory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Laboratory> result = laboratoryService.partialUpdate(laboratory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratory.getId())
        );
    }

    /**
     * {@code GET  /laboratories} : get all the laboratories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of laboratories in body.
     */
    @GetMapping("/laboratories")
    public ResponseEntity<List<Laboratory>> getAllLaboratories(Pageable pageable) {
        log.debug("REST request to get a page of Laboratories");
        Page<Laboratory> page = laboratoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /laboratories/:id} : get the "id" laboratory.
     *
     * @param id the id of the laboratory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the laboratory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratories/{id}")
    public ResponseEntity<Laboratory> getLaboratory(@PathVariable String id) {
        log.debug("REST request to get Laboratory : {}", id);
        Optional<Laboratory> laboratory = laboratoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(laboratory);
    }

    /**
     * {@code DELETE  /laboratories/:id} : delete the "id" laboratory.
     *
     * @param id the id of the laboratory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/laboratories/{id}")
    public ResponseEntity<Void> deleteLaboratory(@PathVariable String id) {
        log.debug("REST request to delete Laboratory : {}", id);
        laboratoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

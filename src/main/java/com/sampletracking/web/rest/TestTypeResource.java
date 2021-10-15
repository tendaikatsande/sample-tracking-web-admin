package com.sampletracking.web.rest;

import com.sampletracking.domain.TestType;
import com.sampletracking.repository.TestTypeRepository;
import com.sampletracking.service.TestTypeService;
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
 * REST controller for managing {@link com.sampletracking.domain.TestType}.
 */
@RestController
@RequestMapping("/api")
public class TestTypeResource {

    private final Logger log = LoggerFactory.getLogger(TestTypeResource.class);

    private static final String ENTITY_NAME = "testType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestTypeService testTypeService;

    private final TestTypeRepository testTypeRepository;

    public TestTypeResource(TestTypeService testTypeService, TestTypeRepository testTypeRepository) {
        this.testTypeService = testTypeService;
        this.testTypeRepository = testTypeRepository;
    }

    /**
     * {@code POST  /test-types} : Create a new testType.
     *
     * @param testType the testType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testType, or with status {@code 400 (Bad Request)} if the testType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/test-types")
    public ResponseEntity<TestType> createTestType(@RequestBody TestType testType) throws URISyntaxException {
        log.debug("REST request to save TestType : {}", testType);
        if (testType.getId() != null) {
            throw new BadRequestAlertException("A new testType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestType result = testTypeService.save(testType);
        return ResponseEntity
            .created(new URI("/api/test-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /test-types/:id} : Updates an existing testType.
     *
     * @param id the id of the testType to save.
     * @param testType the testType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testType,
     * or with status {@code 400 (Bad Request)} if the testType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/test-types/{id}")
    public ResponseEntity<TestType> updateTestType(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody TestType testType
    ) throws URISyntaxException {
        log.debug("REST request to update TestType : {}, {}", id, testType);
        if (testType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TestType result = testTypeService.save(testType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testType.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /test-types/:id} : Partial updates given fields of an existing testType, field will ignore if it is null
     *
     * @param id the id of the testType to save.
     * @param testType the testType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testType,
     * or with status {@code 400 (Bad Request)} if the testType is not valid,
     * or with status {@code 404 (Not Found)} if the testType is not found,
     * or with status {@code 500 (Internal Server Error)} if the testType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/test-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TestType> partialUpdateTestType(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody TestType testType
    ) throws URISyntaxException {
        log.debug("REST request to partial update TestType partially : {}, {}", id, testType);
        if (testType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TestType> result = testTypeService.partialUpdate(testType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testType.getId())
        );
    }

    /**
     * {@code GET  /test-types} : get all the testTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testTypes in body.
     */
    @GetMapping("/test-types")
    public ResponseEntity<List<TestType>> getAllTestTypes(Pageable pageable) {
        log.debug("REST request to get a page of TestTypes");
        Page<TestType> page = testTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-types/:id} : get the "id" testType.
     *
     * @param id the id of the testType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/test-types/{id}")
    public ResponseEntity<TestType> getTestType(@PathVariable String id) {
        log.debug("REST request to get TestType : {}", id);
        Optional<TestType> testType = testTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testType);
    }

    /**
     * {@code DELETE  /test-types/:id} : delete the "id" testType.
     *
     * @param id the id of the testType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/test-types/{id}")
    public ResponseEntity<Void> deleteTestType(@PathVariable String id) {
        log.debug("REST request to delete TestType : {}", id);
        testTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

package uc.dei.mse.supportivecare.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import uc.dei.mse.supportivecare.service.SymptomQueryService;
import uc.dei.mse.supportivecare.service.SymptomService;
import uc.dei.mse.supportivecare.service.dto.SymptomCriteria;
import uc.dei.mse.supportivecare.service.dto.SymptomDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Symptom}.
 */
@RestController
@RequestMapping("/api")
public class SymptomResource {

    private final Logger log = LoggerFactory.getLogger(SymptomResource.class);

    private static final String ENTITY_NAME = "symptom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SymptomService symptomService;

    private final SymptomQueryService symptomQueryService;

    public SymptomResource(SymptomService symptomService, SymptomQueryService symptomQueryService) {
        this.symptomService = symptomService;
        this.symptomQueryService = symptomQueryService;
    }

    /**
     * {@code POST  /symptoms} : Create a new symptom.
     *
     * @param symptomDTO the symptomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new symptomDTO, or with status {@code 400 (Bad Request)} if the symptom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/symptoms")
    public ResponseEntity<SymptomDTO> createSymptom(@Valid @RequestBody SymptomDTO symptomDTO) throws URISyntaxException {
        log.debug("REST request to save Symptom : {}", symptomDTO);
        if (symptomDTO.getId() != null) {
            throw new BadRequestAlertException("A new symptom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SymptomDTO result = symptomService.save(symptomDTO);
        return ResponseEntity
            .created(new URI("/api/symptoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /symptoms} : Updates an existing symptom.
     *
     * @param symptomDTO the symptomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptomDTO,
     * or with status {@code 400 (Bad Request)} if the symptomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the symptomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/symptoms")
    public ResponseEntity<SymptomDTO> updateSymptom(@Valid @RequestBody SymptomDTO symptomDTO) throws URISyntaxException {
        log.debug("REST request to update Symptom : {}", symptomDTO);
        if (symptomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SymptomDTO result = symptomService.save(symptomDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /symptoms} : Updates given fields of an existing symptom.
     *
     * @param symptomDTO the symptomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptomDTO,
     * or with status {@code 400 (Bad Request)} if the symptomDTO is not valid,
     * or with status {@code 404 (Not Found)} if the symptomDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the symptomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/symptoms", consumes = "application/merge-patch+json")
    public ResponseEntity<SymptomDTO> partialUpdateSymptom(@NotNull @RequestBody SymptomDTO symptomDTO) throws URISyntaxException {
        log.debug("REST request to update Symptom partially : {}", symptomDTO);
        if (symptomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<SymptomDTO> result = symptomService.partialUpdate(symptomDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptomDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /symptoms} : get all the symptoms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of symptoms in body.
     */
    @GetMapping("/symptoms")
    public ResponseEntity<List<SymptomDTO>> getAllSymptoms(SymptomCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Symptoms by criteria: {}", criteria);
        Page<SymptomDTO> page = symptomQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /symptoms/count} : count all the symptoms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/symptoms/count")
    public ResponseEntity<Long> countSymptoms(SymptomCriteria criteria) {
        log.debug("REST request to count Symptoms by criteria: {}", criteria);
        return ResponseEntity.ok().body(symptomQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /symptoms/:id} : get the "id" symptom.
     *
     * @param id the id of the symptomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the symptomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/symptoms/{id}")
    public ResponseEntity<SymptomDTO> getSymptom(@PathVariable Long id) {
        log.debug("REST request to get Symptom : {}", id);
        Optional<SymptomDTO> symptomDTO = symptomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(symptomDTO);
    }

    /**
     * {@code DELETE  /symptoms/:id} : delete the "id" symptom.
     *
     * @param id the id of the symptomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/symptoms/{id}")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        log.debug("REST request to delete Symptom : {}", id);
        symptomService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

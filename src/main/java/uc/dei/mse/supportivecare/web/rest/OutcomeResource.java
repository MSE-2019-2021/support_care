package uc.dei.mse.supportivecare.web.rest;

import io.jsonwebtoken.io.IOException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uc.dei.mse.supportivecare.service.DocumentService;
import uc.dei.mse.supportivecare.service.OutcomeQueryService;
import uc.dei.mse.supportivecare.service.OutcomeService;
import uc.dei.mse.supportivecare.service.dto.OutcomeCriteria;
import uc.dei.mse.supportivecare.service.dto.OutcomeDTO;
import uc.dei.mse.supportivecare.service.mapper.DocumentContentMapper;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Outcome}.
 */
@RestController
@RequestMapping("/api")
public class OutcomeResource {

    private final Logger log = LoggerFactory.getLogger(OutcomeResource.class);

    private static final String ENTITY_NAME = "outcome";
    private final DocumentContentMapper documentContentMapper;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutcomeService outcomeService;

    private final OutcomeQueryService outcomeQueryService;

    private final DocumentService documentService;

    public OutcomeResource(
        OutcomeService outcomeService,
        OutcomeQueryService outcomeQueryService,
        DocumentContentMapper documentContentMapper,
        DocumentService documentService
    ) {
        this.outcomeService = outcomeService;
        this.outcomeQueryService = outcomeQueryService;
        this.documentService = documentService;
        this.documentContentMapper = documentContentMapper;
    }

    /**
     * {@code POST  /outcomes} : Create a new outcome.
     *
     * @param outcomeDTO the outcomeDTO to create.
     * @param files the list of files
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outcomeDTO, or with status {@code 400 (Bad Request)} if the outcome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outcomes")
    public ResponseEntity<OutcomeDTO> createOutcome(@Valid @RequestPart OutcomeDTO outcomeDTO, @RequestPart List<MultipartFile> files)
        throws URISyntaxException, IOException {
        log.debug("REST request to save Outcome : {}", outcomeDTO);
        if (outcomeDTO.getId() != null) {
            throw new BadRequestAlertException("A new outcome cannot already have an ID", ENTITY_NAME, "idexists");
        }

        outcomeDTO.setDocuments(documentContentMapper.multiPartFilesToDocuments(files));
        OutcomeDTO result = outcomeService.save(outcomeDTO);

        return ResponseEntity
            .created(new URI("/api/outcomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /outcomes} : Updates an existing outcome.
     *
     * @param outcomeDTO the outcomeDTO to update.
     * @param files the list of files
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outcomeDTO,
     * or with status {@code 400 (Bad Request)} if the outcomeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outcomeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outcomes")
    public ResponseEntity<OutcomeDTO> updateOutcome(@Valid @RequestPart OutcomeDTO outcomeDTO, @RequestPart List<MultipartFile> files)
        throws URISyntaxException {
        log.debug("REST request to update Outcome : {}", outcomeDTO);
        if (outcomeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!files.isEmpty()) {
            outcomeDTO.getDocuments().addAll(documentContentMapper.multiPartFilesToDocuments(files));
        }

        OutcomeDTO result = outcomeService.save(outcomeDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /outcomes} : Updates given fields of an existing outcome.
     *
     * @param outcomeDTO the outcomeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outcomeDTO,
     * or with status {@code 400 (Bad Request)} if the outcomeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the outcomeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the outcomeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/outcomes", consumes = "application/merge-patch+json")
    public ResponseEntity<OutcomeDTO> partialUpdateOutcome(@NotNull @RequestBody OutcomeDTO outcomeDTO) throws URISyntaxException {
        log.debug("REST request to update Outcome partially : {}", outcomeDTO);
        if (outcomeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<OutcomeDTO> result = outcomeService.partialUpdate(outcomeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outcomeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /outcomes} : get all the outcomes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outcomes in body.
     */
    @GetMapping("/outcomes")
    public ResponseEntity<List<OutcomeDTO>> getAllOutcomes(OutcomeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Outcomes by criteria: {}", criteria);
        Page<OutcomeDTO> page = outcomeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /outcomes/count} : count all the outcomes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/outcomes/count")
    public ResponseEntity<Long> countOutcomes(OutcomeCriteria criteria) {
        log.debug("REST request to count Outcomes by criteria: {}", criteria);
        return ResponseEntity.ok().body(outcomeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /outcomes/:id} : get the "id" outcome.
     *
     * @param id the id of the outcomeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outcomeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outcomes/{id}")
    public ResponseEntity<OutcomeDTO> getOutcome(@PathVariable Long id) {
        log.debug("REST request to get Outcome : {}", id);
        Optional<OutcomeDTO> outcomeDTO = outcomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outcomeDTO);
    }

    /**
     * {@code DELETE  /outcomes/:id} : delete the "id" outcome.
     *
     * @param id the id of the outcomeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outcomes/{id}")
    public ResponseEntity<Void> deleteOutcome(@PathVariable Long id) {
        log.debug("REST request to delete Outcome : {}", id);
        outcomeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package uc.dei.mse.supportivecare.web.rest;

import uc.dei.mse.supportivecare.service.DiagnosticService;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;
import uc.dei.mse.supportivecare.service.dto.DiagnosticDTO;
import uc.dei.mse.supportivecare.service.dto.DiagnosticCriteria;
import uc.dei.mse.supportivecare.service.DiagnosticQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Diagnostic}.
 */
@RestController
@RequestMapping("/api")
public class DiagnosticResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosticResource.class);

    private static final String ENTITY_NAME = "diagnostic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiagnosticService diagnosticService;

    private final DiagnosticQueryService diagnosticQueryService;

    public DiagnosticResource(DiagnosticService diagnosticService, DiagnosticQueryService diagnosticQueryService) {
        this.diagnosticService = diagnosticService;
        this.diagnosticQueryService = diagnosticQueryService;
    }

    /**
     * {@code POST  /diagnostics} : Create a new diagnostic.
     *
     * @param diagnosticDTO the diagnosticDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnosticDTO, or with status {@code 400 (Bad Request)} if the diagnostic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diagnostics")
    public ResponseEntity<DiagnosticDTO> createDiagnostic(@Valid @RequestBody DiagnosticDTO diagnosticDTO) throws URISyntaxException {
        log.debug("REST request to save Diagnostic : {}", diagnosticDTO);
        if (diagnosticDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnostic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiagnosticDTO result = diagnosticService.save(diagnosticDTO);
        return ResponseEntity.created(new URI("/api/diagnostics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diagnostics} : Updates an existing diagnostic.
     *
     * @param diagnosticDTO the diagnosticDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosticDTO,
     * or with status {@code 400 (Bad Request)} if the diagnosticDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnosticDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diagnostics")
    public ResponseEntity<DiagnosticDTO> updateDiagnostic(@Valid @RequestBody DiagnosticDTO diagnosticDTO) throws URISyntaxException {
        log.debug("REST request to update Diagnostic : {}", diagnosticDTO);
        if (diagnosticDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiagnosticDTO result = diagnosticService.save(diagnosticDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosticDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /diagnostics} : get all the diagnostics.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnostics in body.
     */
    @GetMapping("/diagnostics")
    public ResponseEntity<List<DiagnosticDTO>> getAllDiagnostics(DiagnosticCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Diagnostics by criteria: {}", criteria);
        Page<DiagnosticDTO> page = diagnosticQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diagnostics/count} : count all the diagnostics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/diagnostics/count")
    public ResponseEntity<Long> countDiagnostics(DiagnosticCriteria criteria) {
        log.debug("REST request to count Diagnostics by criteria: {}", criteria);
        return ResponseEntity.ok().body(diagnosticQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /diagnostics/:id} : get the "id" diagnostic.
     *
     * @param id the id of the diagnosticDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnosticDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diagnostics/{id}")
    public ResponseEntity<DiagnosticDTO> getDiagnostic(@PathVariable Long id) {
        log.debug("REST request to get Diagnostic : {}", id);
        Optional<DiagnosticDTO> diagnosticDTO = diagnosticService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnosticDTO);
    }

    /**
     * {@code DELETE  /diagnostics/:id} : delete the "id" diagnostic.
     *
     * @param id the id of the diagnosticDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diagnostics/{id}")
    public ResponseEntity<Void> deleteDiagnostic(@PathVariable Long id) {
        log.debug("REST request to delete Diagnostic : {}", id);
        diagnosticService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

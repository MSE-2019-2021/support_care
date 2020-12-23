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
import uc.dei.mse.supportivecare.service.ActiveSubstanceQueryService;
import uc.dei.mse.supportivecare.service.ActiveSubstanceService;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceCriteria;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.ActiveSubstance}.
 */
@RestController
@RequestMapping("/api")
public class ActiveSubstanceResource {

    private final Logger log = LoggerFactory.getLogger(ActiveSubstanceResource.class);

    private static final String ENTITY_NAME = "activeSubstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActiveSubstanceService activeSubstanceService;

    private final ActiveSubstanceQueryService activeSubstanceQueryService;

    public ActiveSubstanceResource(ActiveSubstanceService activeSubstanceService, ActiveSubstanceQueryService activeSubstanceQueryService) {
        this.activeSubstanceService = activeSubstanceService;
        this.activeSubstanceQueryService = activeSubstanceQueryService;
    }

    /**
     * {@code POST  /active-substances} : Create a new activeSubstance.
     *
     * @param activeSubstanceDTO the activeSubstanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activeSubstanceDTO, or with status {@code 400 (Bad Request)} if the activeSubstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/active-substances")
    public ResponseEntity<ActiveSubstanceDTO> createActiveSubstance(@Valid @RequestBody ActiveSubstanceDTO activeSubstanceDTO)
        throws URISyntaxException {
        log.debug("REST request to save ActiveSubstance : {}", activeSubstanceDTO);
        if (activeSubstanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new activeSubstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActiveSubstanceDTO result = activeSubstanceService.save(activeSubstanceDTO);
        return ResponseEntity
            .created(new URI("/api/active-substances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /active-substances} : Updates an existing activeSubstance.
     *
     * @param activeSubstanceDTO the activeSubstanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeSubstanceDTO,
     * or with status {@code 400 (Bad Request)} if the activeSubstanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activeSubstanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/active-substances")
    public ResponseEntity<ActiveSubstanceDTO> updateActiveSubstance(@Valid @RequestBody ActiveSubstanceDTO activeSubstanceDTO)
        throws URISyntaxException {
        log.debug("REST request to update ActiveSubstance : {}", activeSubstanceDTO);
        if (activeSubstanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActiveSubstanceDTO result = activeSubstanceService.save(activeSubstanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeSubstanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /active-substances} : Updates given fields of an existing activeSubstance.
     *
     * @param activeSubstanceDTO the activeSubstanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeSubstanceDTO,
     * or with status {@code 400 (Bad Request)} if the activeSubstanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the activeSubstanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the activeSubstanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/active-substances", consumes = "application/merge-patch+json")
    public ResponseEntity<ActiveSubstanceDTO> partialUpdateActiveSubstance(@NotNull @RequestBody ActiveSubstanceDTO activeSubstanceDTO)
        throws URISyntaxException {
        log.debug("REST request to update ActiveSubstance partially : {}", activeSubstanceDTO);
        if (activeSubstanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<ActiveSubstanceDTO> result = activeSubstanceService.partialUpdate(activeSubstanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeSubstanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /active-substances} : get all the activeSubstances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activeSubstances in body.
     */
    @GetMapping("/active-substances")
    public ResponseEntity<List<ActiveSubstanceDTO>> getAllActiveSubstances(ActiveSubstanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ActiveSubstances by criteria: {}", criteria);
        Page<ActiveSubstanceDTO> page = activeSubstanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /active-substances/count} : count all the activeSubstances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/active-substances/count")
    public ResponseEntity<Long> countActiveSubstances(ActiveSubstanceCriteria criteria) {
        log.debug("REST request to count ActiveSubstances by criteria: {}", criteria);
        return ResponseEntity.ok().body(activeSubstanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /active-substances/:id} : get the "id" activeSubstance.
     *
     * @param id the id of the activeSubstanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activeSubstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/active-substances/{id}")
    public ResponseEntity<ActiveSubstanceDTO> getActiveSubstance(@PathVariable Long id) {
        log.debug("REST request to get ActiveSubstance : {}", id);
        Optional<ActiveSubstanceDTO> activeSubstanceDTO = activeSubstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activeSubstanceDTO);
    }

    /**
     * {@code DELETE  /active-substances/:id} : delete the "id" activeSubstance.
     *
     * @param id the id of the activeSubstanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/active-substances/{id}")
    public ResponseEntity<Void> deleteActiveSubstance(@PathVariable Long id) {
        log.debug("REST request to delete ActiveSubstance : {}", id);
        activeSubstanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

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
import uc.dei.mse.supportivecare.service.AdministrationQueryService;
import uc.dei.mse.supportivecare.service.AdministrationService;
import uc.dei.mse.supportivecare.service.dto.AdministrationCriteria;
import uc.dei.mse.supportivecare.service.dto.AdministrationDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Administration}.
 */
@RestController
@RequestMapping("/api")
public class AdministrationResource {

    private final Logger log = LoggerFactory.getLogger(AdministrationResource.class);

    private static final String ENTITY_NAME = "administration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministrationService administrationService;

    private final AdministrationQueryService administrationQueryService;

    public AdministrationResource(AdministrationService administrationService, AdministrationQueryService administrationQueryService) {
        this.administrationService = administrationService;
        this.administrationQueryService = administrationQueryService;
    }

    /**
     * {@code POST  /administrations} : Create a new administration.
     *
     * @param administrationDTO the administrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administrationDTO, or with status {@code 400 (Bad Request)} if the administration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrations")
    public ResponseEntity<AdministrationDTO> createAdministration(@Valid @RequestBody AdministrationDTO administrationDTO)
        throws URISyntaxException {
        log.debug("REST request to save Administration : {}", administrationDTO);
        if (administrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new administration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministrationDTO result = administrationService.save(administrationDTO);
        return ResponseEntity
            .created(new URI("/api/administrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrations} : Updates an existing administration.
     *
     * @param administrationDTO the administrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrationDTO,
     * or with status {@code 400 (Bad Request)} if the administrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrations")
    public ResponseEntity<AdministrationDTO> updateAdministration(@Valid @RequestBody AdministrationDTO administrationDTO)
        throws URISyntaxException {
        log.debug("REST request to update Administration : {}", administrationDTO);
        if (administrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdministrationDTO result = administrationService.save(administrationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /administrations} : Updates given fields of an existing administration.
     *
     * @param administrationDTO the administrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrationDTO,
     * or with status {@code 400 (Bad Request)} if the administrationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the administrationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the administrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/administrations", consumes = "application/merge-patch+json")
    public ResponseEntity<AdministrationDTO> partialUpdateAdministration(@NotNull @RequestBody AdministrationDTO administrationDTO)
        throws URISyntaxException {
        log.debug("REST request to update Administration partially : {}", administrationDTO);
        if (administrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<AdministrationDTO> result = administrationService.partialUpdate(administrationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /administrations} : get all the administrations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrations in body.
     */
    @GetMapping("/administrations")
    public ResponseEntity<List<AdministrationDTO>> getAllAdministrations(AdministrationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Administrations by criteria: {}", criteria);
        Page<AdministrationDTO> page = administrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrations/count} : count all the administrations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/administrations/count")
    public ResponseEntity<Long> countAdministrations(AdministrationCriteria criteria) {
        log.debug("REST request to count Administrations by criteria: {}", criteria);
        return ResponseEntity.ok().body(administrationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /administrations/:id} : get the "id" administration.
     *
     * @param id the id of the administrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrations/{id}")
    public ResponseEntity<AdministrationDTO> getAdministration(@PathVariable Long id) {
        log.debug("REST request to get Administration : {}", id);
        Optional<AdministrationDTO> administrationDTO = administrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrationDTO);
    }

    /**
     * {@code DELETE  /administrations/:id} : delete the "id" administration.
     *
     * @param id the id of the administrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrations/{id}")
    public ResponseEntity<Void> deleteAdministration(@PathVariable Long id) {
        log.debug("REST request to delete Administration : {}", id);
        administrationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.service.DrugService;
import uc.dei.mse.supportcare.web.rest.errors.BadRequestAlertException;
import uc.dei.mse.supportcare.service.dto.DrugDTO;
import uc.dei.mse.supportcare.service.dto.DrugCriteria;
import uc.dei.mse.supportcare.service.DrugQueryService;

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
 * REST controller for managing {@link uc.dei.mse.supportcare.domain.Drug}.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrugService drugService;

    private final DrugQueryService drugQueryService;

    public DrugResource(DrugService drugService, DrugQueryService drugQueryService) {
        this.drugService = drugService;
        this.drugQueryService = drugQueryService;
    }

    /**
     * {@code POST  /drugs} : Create a new drug.
     *
     * @param drugDTO the drugDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drugDTO, or with status {@code 400 (Bad Request)} if the drug has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drugs")
    public ResponseEntity<DrugDTO> createDrug(@Valid @RequestBody DrugDTO drugDTO) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drugDTO);
        if (drugDTO.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugDTO result = drugService.save(drugDTO);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drugs} : Updates an existing drug.
     *
     * @param drugDTO the drugDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drugDTO,
     * or with status {@code 400 (Bad Request)} if the drugDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drugDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drugs")
    public ResponseEntity<DrugDTO> updateDrug(@Valid @RequestBody DrugDTO drugDTO) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drugDTO);
        if (drugDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrugDTO result = drugService.save(drugDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drugDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drugs} : get all the drugs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drugs in body.
     */
    @GetMapping("/drugs")
    public ResponseEntity<List<DrugDTO>> getAllDrugs(DrugCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Drugs by criteria: {}", criteria);
        Page<DrugDTO> page = drugQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drugs/count} : count all the drugs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/drugs/count")
    public ResponseEntity<Long> countDrugs(DrugCriteria criteria) {
        log.debug("REST request to count Drugs by criteria: {}", criteria);
        return ResponseEntity.ok().body(drugQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /drugs/:id} : get the "id" drug.
     *
     * @param id the id of the drugDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drugDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drugs/{id}")
    public ResponseEntity<DrugDTO> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Optional<DrugDTO> drugDTO = drugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drugDTO);
    }

    /**
     * {@code DELETE  /drugs/:id} : delete the "id" drug.
     *
     * @param id the id of the drugDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drugs/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

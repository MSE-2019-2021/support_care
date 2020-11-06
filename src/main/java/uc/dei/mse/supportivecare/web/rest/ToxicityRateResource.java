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
import uc.dei.mse.supportivecare.service.ToxicityRateQueryService;
import uc.dei.mse.supportivecare.service.ToxicityRateService;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateCriteria;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.ToxicityRate}.
 */
@RestController
@RequestMapping("/api")
public class ToxicityRateResource {

    private final Logger log = LoggerFactory.getLogger(ToxicityRateResource.class);

    private static final String ENTITY_NAME = "toxicityRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ToxicityRateService toxicityRateService;

    private final ToxicityRateQueryService toxicityRateQueryService;

    public ToxicityRateResource(ToxicityRateService toxicityRateService, ToxicityRateQueryService toxicityRateQueryService) {
        this.toxicityRateService = toxicityRateService;
        this.toxicityRateQueryService = toxicityRateQueryService;
    }

    /**
     * {@code POST  /toxicity-rates} : Create a new toxicityRate.
     *
     * @param toxicityRateDTO the toxicityRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new toxicityRateDTO, or with status {@code 400 (Bad Request)} if the toxicityRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/toxicity-rates")
    public ResponseEntity<ToxicityRateDTO> createToxicityRate(@Valid @RequestBody ToxicityRateDTO toxicityRateDTO)
        throws URISyntaxException {
        log.debug("REST request to save ToxicityRate : {}", toxicityRateDTO);
        if (toxicityRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new toxicityRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ToxicityRateDTO result = toxicityRateService.save(toxicityRateDTO);
        return ResponseEntity
            .created(new URI("/api/toxicity-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /toxicity-rates} : Updates an existing toxicityRate.
     *
     * @param toxicityRateDTO the toxicityRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toxicityRateDTO,
     * or with status {@code 400 (Bad Request)} if the toxicityRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the toxicityRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/toxicity-rates")
    public ResponseEntity<ToxicityRateDTO> updateToxicityRate(@Valid @RequestBody ToxicityRateDTO toxicityRateDTO)
        throws URISyntaxException {
        log.debug("REST request to update ToxicityRate : {}", toxicityRateDTO);
        if (toxicityRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ToxicityRateDTO result = toxicityRateService.save(toxicityRateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, toxicityRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /toxicity-rates} : Updates given fields of an existing toxicityRate.
     *
     * @param toxicityRateDTO the toxicityRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toxicityRateDTO,
     * or with status {@code 400 (Bad Request)} if the toxicityRateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the toxicityRateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the toxicityRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/toxicity-rates", consumes = "application/merge-patch+json")
    public ResponseEntity<ToxicityRateDTO> partialUpdateToxicityRate(@NotNull @RequestBody ToxicityRateDTO toxicityRateDTO)
        throws URISyntaxException {
        log.debug("REST request to update ToxicityRate partially : {}", toxicityRateDTO);
        if (toxicityRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<ToxicityRateDTO> result = Optional.ofNullable(toxicityRateService.partialUpdate(toxicityRateDTO));

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, toxicityRateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /toxicity-rates} : get all the toxicityRates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of toxicityRates in body.
     */
    @GetMapping("/toxicity-rates")
    public ResponseEntity<List<ToxicityRateDTO>> getAllToxicityRates(ToxicityRateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ToxicityRates by criteria: {}", criteria);
        Page<ToxicityRateDTO> page = toxicityRateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /toxicity-rates/count} : count all the toxicityRates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/toxicity-rates/count")
    public ResponseEntity<Long> countToxicityRates(ToxicityRateCriteria criteria) {
        log.debug("REST request to count ToxicityRates by criteria: {}", criteria);
        return ResponseEntity.ok().body(toxicityRateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /toxicity-rates/:id} : get the "id" toxicityRate.
     *
     * @param id the id of the toxicityRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the toxicityRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/toxicity-rates/{id}")
    public ResponseEntity<ToxicityRateDTO> getToxicityRate(@PathVariable Long id) {
        log.debug("REST request to get ToxicityRate : {}", id);
        Optional<ToxicityRateDTO> toxicityRateDTO = toxicityRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(toxicityRateDTO);
    }

    /**
     * {@code DELETE  /toxicity-rates/:id} : delete the "id" toxicityRate.
     *
     * @param id the id of the toxicityRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/toxicity-rates/{id}")
    public ResponseEntity<Void> deleteToxicityRate(@PathVariable Long id) {
        log.debug("REST request to delete ToxicityRate : {}", id);
        toxicityRateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

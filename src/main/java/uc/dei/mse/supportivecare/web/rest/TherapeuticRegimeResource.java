package uc.dei.mse.supportivecare.web.rest;

import uc.dei.mse.supportivecare.service.TherapeuticRegimeService;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeCriteria;
import uc.dei.mse.supportivecare.service.TherapeuticRegimeQueryService;

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
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.TherapeuticRegime}.
 */
@RestController
@RequestMapping("/api")
public class TherapeuticRegimeResource {

    private final Logger log = LoggerFactory.getLogger(TherapeuticRegimeResource.class);

    private static final String ENTITY_NAME = "therapeuticRegime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TherapeuticRegimeService therapeuticRegimeService;

    private final TherapeuticRegimeQueryService therapeuticRegimeQueryService;

    public TherapeuticRegimeResource(TherapeuticRegimeService therapeuticRegimeService, TherapeuticRegimeQueryService therapeuticRegimeQueryService) {
        this.therapeuticRegimeService = therapeuticRegimeService;
        this.therapeuticRegimeQueryService = therapeuticRegimeQueryService;
    }

    /**
     * {@code POST  /therapeutic-regimes} : Create a new therapeuticRegime.
     *
     * @param therapeuticRegimeDTO the therapeuticRegimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new therapeuticRegimeDTO, or with status {@code 400 (Bad Request)} if the therapeuticRegime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/therapeutic-regimes")
    public ResponseEntity<TherapeuticRegimeDTO> createTherapeuticRegime(@Valid @RequestBody TherapeuticRegimeDTO therapeuticRegimeDTO) throws URISyntaxException {
        log.debug("REST request to save TherapeuticRegime : {}", therapeuticRegimeDTO);
        if (therapeuticRegimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new therapeuticRegime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TherapeuticRegimeDTO result = therapeuticRegimeService.save(therapeuticRegimeDTO);
        return ResponseEntity.created(new URI("/api/therapeutic-regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /therapeutic-regimes} : Updates an existing therapeuticRegime.
     *
     * @param therapeuticRegimeDTO the therapeuticRegimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated therapeuticRegimeDTO,
     * or with status {@code 400 (Bad Request)} if the therapeuticRegimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the therapeuticRegimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/therapeutic-regimes")
    public ResponseEntity<TherapeuticRegimeDTO> updateTherapeuticRegime(@Valid @RequestBody TherapeuticRegimeDTO therapeuticRegimeDTO) throws URISyntaxException {
        log.debug("REST request to update TherapeuticRegime : {}", therapeuticRegimeDTO);
        if (therapeuticRegimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TherapeuticRegimeDTO result = therapeuticRegimeService.save(therapeuticRegimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, therapeuticRegimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /therapeutic-regimes} : get all the therapeuticRegimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of therapeuticRegimes in body.
     */
    @GetMapping("/therapeutic-regimes")
    public ResponseEntity<List<TherapeuticRegimeDTO>> getAllTherapeuticRegimes(TherapeuticRegimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TherapeuticRegimes by criteria: {}", criteria);
        Page<TherapeuticRegimeDTO> page = therapeuticRegimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /therapeutic-regimes/count} : count all the therapeuticRegimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/therapeutic-regimes/count")
    public ResponseEntity<Long> countTherapeuticRegimes(TherapeuticRegimeCriteria criteria) {
        log.debug("REST request to count TherapeuticRegimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(therapeuticRegimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /therapeutic-regimes/:id} : get the "id" therapeuticRegime.
     *
     * @param id the id of the therapeuticRegimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the therapeuticRegimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/therapeutic-regimes/{id}")
    public ResponseEntity<TherapeuticRegimeDTO> getTherapeuticRegime(@PathVariable Long id) {
        log.debug("REST request to get TherapeuticRegime : {}", id);
        Optional<TherapeuticRegimeDTO> therapeuticRegimeDTO = therapeuticRegimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(therapeuticRegimeDTO);
    }

    /**
     * {@code DELETE  /therapeutic-regimes/:id} : delete the "id" therapeuticRegime.
     *
     * @param id the id of the therapeuticRegimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/therapeutic-regimes/{id}")
    public ResponseEntity<Void> deleteTherapeuticRegime(@PathVariable Long id) {
        log.debug("REST request to delete TherapeuticRegime : {}", id);
        therapeuticRegimeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

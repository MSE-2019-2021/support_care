package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.service.GuideService;
import uc.dei.mse.supportcare.web.rest.errors.BadRequestAlertException;
import uc.dei.mse.supportcare.service.dto.GuideDTO;
import uc.dei.mse.supportcare.service.dto.GuideCriteria;
import uc.dei.mse.supportcare.service.GuideQueryService;

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
 * REST controller for managing {@link uc.dei.mse.supportcare.domain.Guide}.
 */
@RestController
@RequestMapping("/api")
public class GuideResource {

    private final Logger log = LoggerFactory.getLogger(GuideResource.class);

    private static final String ENTITY_NAME = "guide";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuideService guideService;

    private final GuideQueryService guideQueryService;

    public GuideResource(GuideService guideService, GuideQueryService guideQueryService) {
        this.guideService = guideService;
        this.guideQueryService = guideQueryService;
    }

    /**
     * {@code POST  /guides} : Create a new guide.
     *
     * @param guideDTO the guideDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guideDTO, or with status {@code 400 (Bad Request)} if the guide has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guides")
    public ResponseEntity<GuideDTO> createGuide(@Valid @RequestBody GuideDTO guideDTO) throws URISyntaxException {
        log.debug("REST request to save Guide : {}", guideDTO);
        if (guideDTO.getId() != null) {
            throw new BadRequestAlertException("A new guide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuideDTO result = guideService.save(guideDTO);
        return ResponseEntity.created(new URI("/api/guides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guides} : Updates an existing guide.
     *
     * @param guideDTO the guideDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guideDTO,
     * or with status {@code 400 (Bad Request)} if the guideDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guideDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guides")
    public ResponseEntity<GuideDTO> updateGuide(@Valid @RequestBody GuideDTO guideDTO) throws URISyntaxException {
        log.debug("REST request to update Guide : {}", guideDTO);
        if (guideDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GuideDTO result = guideService.save(guideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guideDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /guides} : get all the guides.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guides in body.
     */
    @GetMapping("/guides")
    public ResponseEntity<List<GuideDTO>> getAllGuides(GuideCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Guides by criteria: {}", criteria);
        Page<GuideDTO> page = guideQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guides/count} : count all the guides.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/guides/count")
    public ResponseEntity<Long> countGuides(GuideCriteria criteria) {
        log.debug("REST request to count Guides by criteria: {}", criteria);
        return ResponseEntity.ok().body(guideQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /guides/:id} : get the "id" guide.
     *
     * @param id the id of the guideDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guideDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guides/{id}")
    public ResponseEntity<GuideDTO> getGuide(@PathVariable Long id) {
        log.debug("REST request to get Guide : {}", id);
        Optional<GuideDTO> guideDTO = guideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guideDTO);
    }

    /**
     * {@code DELETE  /guides/:id} : delete the "id" guide.
     *
     * @param id the id of the guideDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guides/{id}")
    public ResponseEntity<Void> deleteGuide(@PathVariable Long id) {
        log.debug("REST request to delete Guide : {}", id);
        guideService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

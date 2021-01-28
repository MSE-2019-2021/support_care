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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uc.dei.mse.supportivecare.config.Constants;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.security.SecurityUtils;
import uc.dei.mse.supportivecare.service.ThumbQueryService;
import uc.dei.mse.supportivecare.service.ThumbService;
import uc.dei.mse.supportivecare.service.dto.ThumbCountDTO;
import uc.dei.mse.supportivecare.service.dto.ThumbCriteria;
import uc.dei.mse.supportivecare.service.dto.ThumbDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Thumb}.
 */
@RestController
@RequestMapping("/api")
public class ThumbResource {

    private final Logger log = LoggerFactory.getLogger(ThumbResource.class);

    private static final String ENTITY_NAME = "thumb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThumbService thumbService;

    private final ThumbQueryService thumbQueryService;

    public ThumbResource(ThumbService thumbService, ThumbQueryService thumbQueryService) {
        this.thumbService = thumbService;
        this.thumbQueryService = thumbQueryService;
    }

    /**
     * {@code POST  /thumbs} : Create a new thumb.
     *
     * @param thumbDTO the thumbDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thumbDTO, or with status {@code 400 (Bad Request)} if the thumb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thumbs")
    public ResponseEntity<ThumbDTO> createThumb(@Valid @RequestBody ThumbDTO thumbDTO) throws URISyntaxException {
        log.debug("REST request to save Thumb : {}", thumbDTO);
        if (thumbDTO.getId() != null) {
            throw new BadRequestAlertException("A new thumb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThumbDTO result = thumbService.save(thumbDTO);
        return ResponseEntity
            .created(new URI("/api/thumbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thumbs} : Updates an existing thumb.
     *
     * @param thumbDTO the thumbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thumbDTO,
     * or with status {@code 400 (Bad Request)} if the thumbDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thumbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thumbs")
    public ResponseEntity<ThumbDTO> updateThumb(@Valid @RequestBody ThumbDTO thumbDTO) throws URISyntaxException {
        log.debug("REST request to update Thumb : {}", thumbDTO);
        if (thumbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThumbDTO result = thumbService.save(thumbDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thumbDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thumbs} : Updates given fields of an existing thumb.
     *
     * @param thumbDTO the thumbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thumbDTO,
     * or with status {@code 400 (Bad Request)} if the thumbDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thumbDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thumbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thumbs", consumes = "application/merge-patch+json")
    public ResponseEntity<ThumbDTO> partialUpdateThumb(@NotNull @RequestBody ThumbDTO thumbDTO) throws URISyntaxException {
        log.debug("REST request to update Thumb partially : {}", thumbDTO);
        if (thumbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<ThumbDTO> result = thumbService.partialUpdate(thumbDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thumbDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /thumbs} : get all the thumbs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thumbs in body.
     */
    @GetMapping("/thumbs")
    public ResponseEntity<List<ThumbDTO>> getAllThumbs(ThumbCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Thumbs by criteria: {}", criteria);
        Page<ThumbDTO> page = thumbQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thumbs/count} : count all the thumbs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/thumbs/count")
    public ResponseEntity<Long> countThumbs(ThumbCriteria criteria) {
        log.debug("REST request to count Thumbs by criteria: {}", criteria);
        return ResponseEntity.ok().body(thumbQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /thumbs/:id} : get the "id" thumb.
     *
     * @param id the id of the thumbDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thumbDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thumbs/{id}")
    public ResponseEntity<ThumbDTO> getThumb(@PathVariable Long id) {
        log.debug("REST request to get Thumb : {}", id);
        Optional<ThumbDTO> thumbDTO = thumbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thumbDTO);
    }

    /**
     * {@code DELETE  /thumbs/:id} : delete the "id" thumb.
     *
     * @param id the id of the thumbDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thumbs/{id}")
    public ResponseEntity<Void> deleteThumb(@PathVariable Long id) {
        log.debug("REST request to delete Thumb : {}", id);
        thumbService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /thumbs/:entityType/:entityId/count} : count all thumbs for an Entity.
     *
     * @param entityType the entity type of the thumb to count.
     * @param entityId the entity id of the thumb to count.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thumbCountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thumbs/{entityType}/{entityId}/count")
    public ResponseEntity<ThumbCountDTO> countThumbsFromEntity(@PathVariable EntityFeedback entityType, @PathVariable Long entityId) {
        log.debug("REST request to count Feedbacks for entity name and Id: {} {}", entityType, entityId);
        String currentUser = SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM);
        return ResponseEntity.ok().body(thumbService.countFeedbacksFromEntity(entityType, entityId, currentUser));
    }

    /**
     * {@code POST  /thumbs/:entityType/:entityId} : Create a new thumb for an Entity.
     *
     * @param entityType the entity type of the thumbDTO to save.
     * @param entityId the entity id of the thumbDTO to save.
     * @param thumbDTO the thumbDTO to save.
     * @return the {@link ResponseEntity} with status:
     *      - {@code 201 (Created)} or,
     *      - {@code 200 (Updated)} or,
     *      - {@code 204 (NO_CONTENT)} or,
     *      - {@code 400 (Bad Request)} if the thumb is incorrect.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thumbs/{entityType}/{entityId}")
    public ResponseEntity<Void> manageThumbFromEntity(
        @PathVariable EntityFeedback entityType,
        @PathVariable Long entityId,
        @Valid @RequestBody ThumbDTO thumbDTO
    ) throws URISyntaxException {
        log.debug("REST request to manage Thumb for entity type and Id: {} {}: {}", entityType, entityId, thumbDTO);
        if (!thumbDTO.getEntityType().equals(entityType) || !thumbDTO.getEntityId().equals(entityId)) {
            throw new BadRequestAlertException("The thumb is incorrect", ENTITY_NAME, "wrongEntity");
        }
        String currentUser = SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM);
        if (thumbDTO.getCreatedBy() == null) {
            thumbDTO.setCreatedBy(currentUser);
        } else if (!currentUser.equals(thumbDTO.getCreatedBy())) {
            throw new BadRequestAlertException("The feedback do not belongs to the current user", ENTITY_NAME, "wrongUser");
        }
        return ResponseEntity.status(thumbService.manageThumbFromEntity(thumbDTO)).build();
    }
}

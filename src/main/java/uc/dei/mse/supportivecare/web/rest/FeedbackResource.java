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
import uc.dei.mse.supportivecare.config.Constants;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.security.SecurityUtils;
import uc.dei.mse.supportivecare.service.FeedbackQueryService;
import uc.dei.mse.supportivecare.service.FeedbackService;
import uc.dei.mse.supportivecare.service.dto.FeedbackCriteria;
import uc.dei.mse.supportivecare.service.dto.FeedbackDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Feedback}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackService feedbackService;

    private final FeedbackQueryService feedbackQueryService;

    public FeedbackResource(FeedbackService feedbackService, FeedbackQueryService feedbackQueryService) {
        this.feedbackService = feedbackService;
        this.feedbackQueryService = feedbackQueryService;
    }

    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param feedbackDTO the feedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackDTO, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackDTO> createFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity
            .created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedbacks} : Updates an existing feedback.
     *
     * @param feedbackDTO the feedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedbacks")
    public ResponseEntity<FeedbackDTO> updateFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedbackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedbacks} : Updates given fields of an existing feedback.
     *
     * @param feedbackDTO the feedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedbacks", consumes = "application/merge-patch+json")
    public ResponseEntity<FeedbackDTO> partialUpdateFeedback(@NotNull @RequestBody FeedbackDTO feedbackDTO) throws URISyntaxException {
        log.debug("REST request to update Feedback partially : {}", feedbackDTO);
        if (feedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<FeedbackDTO> result = feedbackService.partialUpdate(feedbackDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedbackDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /feedbacks} : get all the feedbacks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks(FeedbackCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Feedbacks by criteria: {}", criteria);
        Page<FeedbackDTO> page = feedbackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedbacks/count} : count all the feedbacks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedbacks/count")
    public ResponseEntity<Long> countFeedbacks(FeedbackCriteria criteria) {
        log.debug("REST request to count Feedbacks by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedbacks/:id} : get the "id" feedback.
     *
     * @param id the id of the feedbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackDTO> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<FeedbackDTO> feedbackDTO = feedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackDTO);
    }

    /**
     * {@code DELETE  /feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param entityName the entity name of the feedbackDTO to save.
     * @param entityId the entity id of the feedbackDTO to save.
     * @param feedbackDTO the feedbackDTO to save.
     * @return the {@link ResponseEntity} with status:
     *      - {@code 201 (Created)} or,
     *      - {@code 200 (Updated)} or,
     *      - {@code 204 (NO_CONTENT)} or,
     *      - {@code 400 (Bad Request)} if the feedback is incorrect.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks/{entityName}/{entityId}")
    public ResponseEntity<Void> manageFeedbackFromUser(
        @PathVariable EntityFeedback entityName,
        @PathVariable Long entityId,
        @Valid @RequestBody FeedbackDTO feedbackDTO
    ) throws URISyntaxException {
        log.debug("REST request to manage Feedback for entity name and Id: {} {}: {}", entityName, entityId, feedbackDTO);
        if (feedbackDTO.getEntityName() != entityName || feedbackDTO.getEntityId() != entityId) {
            throw new BadRequestAlertException("The feedback is incorrect", ENTITY_NAME, "wrongEntity");
        }
        String currentUser = SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM);
        if (feedbackDTO.getCreatedBy() == null) {
            feedbackDTO.setCreatedBy(currentUser);
        } else if (!currentUser.equals(feedbackDTO.getCreatedBy())) {
            throw new BadRequestAlertException("The feedback do not belongs to the current user", ENTITY_NAME, "wrongUser");
        }
        return ResponseEntity.status(feedbackService.manage(feedbackDTO)).build();
    }
}

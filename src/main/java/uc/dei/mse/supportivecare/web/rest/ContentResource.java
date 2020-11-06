package uc.dei.mse.supportivecare.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
import uc.dei.mse.supportivecare.service.ContentQueryService;
import uc.dei.mse.supportivecare.service.ContentService;
import uc.dei.mse.supportivecare.service.dto.ContentCriteria;
import uc.dei.mse.supportivecare.service.dto.ContentDTO;
import uc.dei.mse.supportivecare.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uc.dei.mse.supportivecare.domain.Content}.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);

    private static final String ENTITY_NAME = "content";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentService contentService;

    private final ContentQueryService contentQueryService;

    public ContentResource(ContentService contentService, ContentQueryService contentQueryService) {
        this.contentService = contentService;
        this.contentQueryService = contentQueryService;
    }

    /**
     * {@code POST  /contents} : Create a new content.
     *
     * @param contentDTO the contentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentDTO, or with status {@code 400 (Bad Request)} if the content has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contents")
    public ResponseEntity<ContentDTO> createContent(@Valid @RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to save Content : {}", contentDTO);
        if (contentDTO.getId() != null) {
            throw new BadRequestAlertException("A new content cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity
            .created(new URI("/api/contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contents} : Updates an existing content.
     *
     * @param contentDTO the contentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentDTO,
     * or with status {@code 400 (Bad Request)} if the contentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contents")
    public ResponseEntity<ContentDTO> updateContent(@Valid @RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to update Content : {}", contentDTO);
        if (contentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contents} : Updates given fields of an existing content.
     *
     * @param contentDTO the contentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentDTO,
     * or with status {@code 400 (Bad Request)} if the contentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contents", consumes = "application/merge-patch+json")
    public ResponseEntity<ContentDTO> partialUpdateContent(@NotNull @RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to update Content partially : {}", contentDTO);
        if (contentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<ContentDTO> result = Optional.ofNullable(contentService.partialUpdate(contentDTO));

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contents} : get all the contents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contents in body.
     */
    @GetMapping("/contents")
    public ResponseEntity<List<ContentDTO>> getAllContents(ContentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Contents by criteria: {}", criteria);
        Page<ContentDTO> page = contentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contents/count} : count all the contents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contents/count")
    public ResponseEntity<Long> countContents(ContentCriteria criteria) {
        log.debug("REST request to count Contents by criteria: {}", criteria);
        return ResponseEntity.ok().body(contentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contents/:id} : get the "id" content.
     *
     * @param id the id of the contentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contents/{id}")
    public ResponseEntity<ContentDTO> getContent(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        Optional<ContentDTO> contentDTO = contentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentDTO);
    }

    /**
     * {@code DELETE  /contents/:id} : delete the "id" content.
     *
     * @param id the id of the contentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contents/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

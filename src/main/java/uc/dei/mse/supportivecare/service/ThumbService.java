package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Thumb;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.repository.ThumbRepository;
import uc.dei.mse.supportivecare.service.dto.ThumbCountDTO;
import uc.dei.mse.supportivecare.service.dto.ThumbDTO;
import uc.dei.mse.supportivecare.service.mapper.ThumbCountMapper;
import uc.dei.mse.supportivecare.service.mapper.ThumbMapper;

/**
 * Service Implementation for managing {@link Thumb}.
 */
@Service
@Transactional
public class ThumbService {

    private final Logger log = LoggerFactory.getLogger(ThumbService.class);

    private final ThumbRepository thumbRepository;

    private final ThumbMapper thumbMapper;

    private final ThumbCountMapper thumbCountMapper;

    public ThumbService(ThumbRepository thumbRepository, ThumbMapper thumbMapper, ThumbCountMapper thumbCountMapper) {
        this.thumbRepository = thumbRepository;
        this.thumbMapper = thumbMapper;
        this.thumbCountMapper = thumbCountMapper;
    }

    /**
     * Save a thumb.
     *
     * @param thumbDTO the entity to save.
     * @return the persisted entity.
     */
    public ThumbDTO save(ThumbDTO thumbDTO) {
        log.debug("Request to save Thumb : {}", thumbDTO);
        Thumb thumb = thumbMapper.toEntity(thumbDTO);
        thumb = thumbRepository.save(thumb);
        return thumbMapper.toDto(thumb);
    }

    /**
     * Partially update a thumb.
     *
     * @param thumbDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ThumbDTO> partialUpdate(ThumbDTO thumbDTO) {
        log.debug("Request to partially update Thumb : {}", thumbDTO);

        return thumbRepository
            .findById(thumbDTO.getId())
            .map(
                existingThumb -> {
                    if (thumbDTO.getEntityType() != null) {
                        existingThumb.setEntityType(thumbDTO.getEntityType());
                    }

                    if (thumbDTO.getEntityId() != null) {
                        existingThumb.setEntityId(thumbDTO.getEntityId());
                    }

                    if (thumbDTO.getThumb() != null) {
                        existingThumb.setThumb(thumbDTO.getThumb());
                    }

                    return existingThumb;
                }
            )
            .map(thumbRepository::save)
            .map(thumbMapper::toDto);
    }

    /**
     * Get all the thumbs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ThumbDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Thumbs");
        return thumbRepository.findAll(pageable).map(thumbMapper::toDto);
    }

    /**
     * Get one thumb by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ThumbDTO> findOne(Long id) {
        log.debug("Request to get Thumb : {}", id);
        return thumbRepository.findById(id).map(thumbMapper::toDto);
    }

    /**
     * Delete the thumb by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Thumb : {}", id);
        thumbRepository.deleteById(id);
    }

    /**
     * Delete the thumb by entity id.
     *
     * @param entityFeedback the entity feedback.
     * @param entityId the entity id.
     */
    public void deleteByEntityTypeAndEntityId(EntityFeedback entityFeedback, Long entityId) {
        log.debug("Request to delete Thumb by Entity Id: {} {}", entityFeedback, entityId);
        thumbRepository.deleteByEntityTypeAndEntityId(entityFeedback, entityId);
    }

    /**
     * Count likes/dislikes thumbs by the entity type, entity id and user.
     *
     * @param entityFeedback the entity feedback.
     * @param entityId the entity id.
     * @param currentUser the current user.
     * @return the thumb detail.
     */
    @Transactional(readOnly = true)
    public ThumbCountDTO countFeedbacksFromEntity(EntityFeedback entityFeedback, Long entityId, String currentUser) {
        log.debug("Request to count Thumbs for entity type and Id, and User: {} {} {}", entityFeedback, entityId, currentUser);
        return thumbCountMapper.toDTO(
            thumbRepository.countAllByEntityTypeAndEntityIdAndCreatedBy(entityFeedback.getValue(), entityId, currentUser)
        );
    }

    /**
     * Manage a thumb by the entity type, entity id and user.
     *
     * @param thumbDTO the entity to manage.
     * @return the HttpStatus:
     *      - {@code 201 (Created)} and with body the new thumbDTO or,
     *      - {@code 200 (Updated)} and with body the new thumbDTO or,
     *      - {@code 204 (NO_CONTENT)}
     */
    public HttpStatus manageThumbFromEntity(ThumbDTO thumbDTO) {
        HttpStatus status;
        if (thumbDTO.getThumb() == null) {
            log.debug("Request to delete Thumb : {}", thumbDTO);
            thumbRepository.deleteByEntityTypeAndEntityIdAndCreatedBy(
                thumbDTO.getEntityType(),
                thumbDTO.getEntityId(),
                thumbDTO.getCreatedBy()
            );
            status = HttpStatus.NO_CONTENT;
        } else {
            if (
                thumbRepository.existsByEntityTypeAndEntityIdAndCreatedBy(
                    thumbDTO.getEntityType(),
                    thumbDTO.getEntityId(),
                    thumbDTO.getCreatedBy()
                )
            ) {
                thumbRepository.deleteByEntityTypeAndEntityIdAndCreatedBy(
                    thumbDTO.getEntityType(),
                    thumbDTO.getEntityId(),
                    thumbDTO.getCreatedBy()
                );
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.CREATED;
            }
            this.save(thumbDTO);
        }
        return status;
    }
}

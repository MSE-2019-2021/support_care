package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Feedback;
import uc.dei.mse.supportivecare.repository.FeedbackRepository;
import uc.dei.mse.supportivecare.service.dto.FeedbackDTO;
import uc.dei.mse.supportivecare.service.mapper.FeedbackMapper;

/**
 * Service Implementation for managing {@link Feedback}.
 */
@Service
@Transactional
public class FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackService.class);

    private final FeedbackRepository feedbackRepository;

    private final FeedbackMapper feedbackMapper;

    public FeedbackService(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    /**
     * Save a feedback.
     *
     * @param feedbackDTO the entity to save.
     * @return the persisted entity.
     */
    public FeedbackDTO save(FeedbackDTO feedbackDTO) {
        log.debug("Request to save Feedback : {}", feedbackDTO);
        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(feedback);
    }

    /**
     * Partially udpates a feedback.
     *
     * @param feedbackDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedbackDTO> partialUpdate(FeedbackDTO feedbackDTO) {
        log.debug("Request to partially update Feedback : {}", feedbackDTO);

        return feedbackRepository
            .findById(feedbackDTO.getId())
            .map(
                existingFeedback -> {
                    if (feedbackDTO.getEntityName() != null) {
                        existingFeedback.setEntityName(feedbackDTO.getEntityName());
                    }

                    if (feedbackDTO.getEntityId() != null) {
                        existingFeedback.setEntityId(feedbackDTO.getEntityId());
                    }

                    if (feedbackDTO.getThumb() != null) {
                        existingFeedback.setThumb(feedbackDTO.getThumb());
                    }

                    if (feedbackDTO.getReason() != null) {
                        existingFeedback.setReason(feedbackDTO.getReason());
                    }

                    if (feedbackDTO.getSolved() != null) {
                        existingFeedback.setSolved(feedbackDTO.getSolved());
                    }

                    if (feedbackDTO.getAnonym() != null) {
                        existingFeedback.setAnonym(feedbackDTO.getAnonym());
                    }

                    return existingFeedback;
                }
            )
            .map(feedbackRepository::save)
            .map(feedbackMapper::toDto);
    }

    /**
     * Get all the feedbacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Feedbacks");
        return feedbackRepository.findAll(pageable).map(feedbackMapper::toDto);
    }

    /**
     * Get one feedback by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackDTO> findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        return feedbackRepository.findById(id).map(feedbackMapper::toDto);
    }

    /**
     * Delete the feedback by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
    }
}

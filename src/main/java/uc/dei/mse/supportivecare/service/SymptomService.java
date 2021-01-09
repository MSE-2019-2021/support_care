package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Symptom;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.repository.SymptomRepository;
import uc.dei.mse.supportivecare.service.dto.SymptomDTO;
import uc.dei.mse.supportivecare.service.mapper.SymptomMapper;

/**
 * Service Implementation for managing {@link Symptom}.
 */
@Service
@Transactional
public class SymptomService {

    private final Logger log = LoggerFactory.getLogger(SymptomService.class);

    private final SymptomRepository symptomRepository;

    private final SymptomMapper symptomMapper;

    private final FeedbackService feedbackService;

    public SymptomService(SymptomRepository symptomRepository, SymptomMapper symptomMapper, FeedbackService feedbackService) {
        this.symptomRepository = symptomRepository;
        this.symptomMapper = symptomMapper;
        this.feedbackService = feedbackService;
    }

    /**
     * Save a symptom.
     *
     * @param symptomDTO the entity to save.
     * @return the persisted entity.
     */
    public SymptomDTO save(SymptomDTO symptomDTO) {
        log.debug("Request to save Symptom : {}", symptomDTO);
        Symptom symptom = symptomMapper.toEntity(symptomDTO);
        symptom = symptomRepository.save(symptom);
        return symptomMapper.toDto(symptom);
    }

    /**
     * Partially udpates a symptom.
     *
     * @param symptomDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SymptomDTO> partialUpdate(SymptomDTO symptomDTO) {
        log.debug("Request to partially update Symptom : {}", symptomDTO);

        return symptomRepository
            .findById(symptomDTO.getId())
            .map(
                existingSymptom -> {
                    if (symptomDTO.getName() != null) {
                        existingSymptom.setName(symptomDTO.getName());
                    }

                    if (symptomDTO.getNotice() != null) {
                        existingSymptom.setNotice(symptomDTO.getNotice());
                    }

                    return existingSymptom;
                }
            )
            .map(symptomRepository::save)
            .map(symptomMapper::toDto);
    }

    /**
     * Get all the symptoms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SymptomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Symptoms");
        return symptomRepository.findAll(pageable).map(symptomMapper::toDto);
    }

    /**
     * Get all the symptoms with eager load of many-to-many relationships.
     *
     * @param pageable the page.
     * @return the list of entities.
     */
    public Page<SymptomDTO> findAllWithEagerRelationships(Pageable pageable) {
        return symptomRepository.findAllWithEagerRelationships(pageable).map(symptomMapper::toDto);
    }

    /**
     * Get one symptom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SymptomDTO> findOne(Long id) {
        log.debug("Request to get Symptom : {}", id);
        return symptomRepository.findOneWithEagerRelationships(id).map(symptomMapper::toDto);
    }

    /**
     * Delete the symptom by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Symptom : {}", id);
        symptomRepository.deleteById(id);
        feedbackService.deleteByEntityNameAndEntityId(EntityFeedback.SYMPTOM, id);
    }
}

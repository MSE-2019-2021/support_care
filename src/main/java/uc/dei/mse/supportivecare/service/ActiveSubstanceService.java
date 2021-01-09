package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.ActiveSubstance;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.repository.ActiveSubstanceRepository;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceDTO;
import uc.dei.mse.supportivecare.service.mapper.ActiveSubstanceMapper;

/**
 * Service Implementation for managing {@link ActiveSubstance}.
 */
@Service
@Transactional
public class ActiveSubstanceService {

    private final Logger log = LoggerFactory.getLogger(ActiveSubstanceService.class);

    private final ActiveSubstanceRepository activeSubstanceRepository;

    private final ActiveSubstanceMapper activeSubstanceMapper;

    private final FeedbackService feedbackService;

    public ActiveSubstanceService(
        ActiveSubstanceRepository activeSubstanceRepository,
        ActiveSubstanceMapper activeSubstanceMapper,
        FeedbackService feedbackService
    ) {
        this.activeSubstanceRepository = activeSubstanceRepository;
        this.activeSubstanceMapper = activeSubstanceMapper;
        this.feedbackService = feedbackService;
    }

    /**
     * Save a activeSubstance.
     *
     * @param activeSubstanceDTO the entity to save.
     * @return the persisted entity.
     */
    public ActiveSubstanceDTO save(ActiveSubstanceDTO activeSubstanceDTO) {
        log.debug("Request to save ActiveSubstance : {}", activeSubstanceDTO);
        ActiveSubstance activeSubstance = activeSubstanceMapper.toEntity(activeSubstanceDTO);
        activeSubstance = activeSubstanceRepository.save(activeSubstance);
        return activeSubstanceMapper.toDto(activeSubstance);
    }

    /**
     * Partially udpates a activeSubstance.
     *
     * @param activeSubstanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActiveSubstanceDTO> partialUpdate(ActiveSubstanceDTO activeSubstanceDTO) {
        log.debug("Request to partially update ActiveSubstance : {}", activeSubstanceDTO);

        return activeSubstanceRepository
            .findById(activeSubstanceDTO.getId())
            .map(
                existingActiveSubstance -> {
                    if (activeSubstanceDTO.getName() != null) {
                        existingActiveSubstance.setName(activeSubstanceDTO.getName());
                    }

                    if (activeSubstanceDTO.getDosage() != null) {
                        existingActiveSubstance.setDosage(activeSubstanceDTO.getDosage());
                    }

                    if (activeSubstanceDTO.getForm() != null) {
                        existingActiveSubstance.setForm(activeSubstanceDTO.getForm());
                    }

                    if (activeSubstanceDTO.getDescription() != null) {
                        existingActiveSubstance.setDescription(activeSubstanceDTO.getDescription());
                    }

                    return existingActiveSubstance;
                }
            )
            .map(activeSubstanceRepository::save)
            .map(activeSubstanceMapper::toDto);
    }

    /**
     * Get all the activeSubstances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActiveSubstanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActiveSubstances");
        return activeSubstanceRepository.findAll(pageable).map(activeSubstanceMapper::toDto);
    }

    /**
     * Get one activeSubstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActiveSubstanceDTO> findOne(Long id) {
        log.debug("Request to get ActiveSubstance : {}", id);
        return activeSubstanceRepository.findById(id).map(activeSubstanceMapper::toDto);
    }

    /**
     * Delete the activeSubstance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActiveSubstance : {}", id);
        activeSubstanceRepository.deleteById(id);
        feedbackService.deleteByEntityNameAndEntityId(EntityFeedback.ACTIVE_SUBSTANCE, id);
    }
}

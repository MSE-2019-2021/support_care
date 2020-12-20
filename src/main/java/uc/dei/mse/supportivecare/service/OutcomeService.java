package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Outcome;
import uc.dei.mse.supportivecare.repository.OutcomeRepository;
import uc.dei.mse.supportivecare.service.dto.OutcomeDTO;
import uc.dei.mse.supportivecare.service.mapper.OutcomeMapper;

/**
 * Service Implementation for managing {@link Outcome}.
 */
@Service
@Transactional
public class OutcomeService {

    private final Logger log = LoggerFactory.getLogger(OutcomeService.class);

    private final OutcomeRepository outcomeRepository;

    private final OutcomeMapper outcomeMapper;

    public OutcomeService(OutcomeRepository outcomeRepository, OutcomeMapper outcomeMapper) {
        this.outcomeRepository = outcomeRepository;
        this.outcomeMapper = outcomeMapper;
    }

    /**
     * Save a outcome.
     *
     * @param outcomeDTO the entity to save.
     * @return the persisted entity.
     */
    public OutcomeDTO save(OutcomeDTO outcomeDTO) {
        log.debug("Request to save Outcome : {}", outcomeDTO);
        Outcome outcome = outcomeMapper.toEntity(outcomeDTO);
        outcome = outcomeRepository.save(outcome);
        return outcomeMapper.toDto(outcome);
    }

    /**
     * Partially udpates a outcome.
     *
     * @param outcomeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OutcomeDTO> partialUpdate(OutcomeDTO outcomeDTO) {
        log.debug("Request to partially update Outcome : {}", outcomeDTO);

        return outcomeRepository
            .findById(outcomeDTO.getId())
            .map(
                existingOutcome -> {
                    if (outcomeDTO.getName() != null) {
                        existingOutcome.setName(outcomeDTO.getName());
                    }

                    if (outcomeDTO.getDescription() != null) {
                        existingOutcome.setDescription(outcomeDTO.getDescription());
                    }

                    return existingOutcome;
                }
            )
            .map(outcomeRepository::save)
            .map(outcomeMapper::toDto);
    }

    /**
     * Get all the outcomes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OutcomeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Outcomes");
        return outcomeRepository.findAll(pageable).map(outcomeMapper::toDto);
    }

    /**
     * Get one outcome by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OutcomeDTO> findOne(Long id) {
        log.debug("Request to get Outcome : {}", id);
        return outcomeRepository.findById(id).map(outcomeMapper::toDto);
    }

    /**
     * Delete the outcome by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Outcome : {}", id);
        outcomeRepository.deleteById(id);
    }
}

package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.ToxicityRate;
import uc.dei.mse.supportivecare.repository.ToxicityRateRepository;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateDTO;
import uc.dei.mse.supportivecare.service.mapper.ToxicityRateMapper;

/**
 * Service Implementation for managing {@link ToxicityRate}.
 */
@Service
@Transactional
public class ToxicityRateService {

    private final Logger log = LoggerFactory.getLogger(ToxicityRateService.class);

    private final ToxicityRateRepository toxicityRateRepository;

    private final ToxicityRateMapper toxicityRateMapper;

    public ToxicityRateService(ToxicityRateRepository toxicityRateRepository, ToxicityRateMapper toxicityRateMapper) {
        this.toxicityRateRepository = toxicityRateRepository;
        this.toxicityRateMapper = toxicityRateMapper;
    }

    /**
     * Save a toxicityRate.
     *
     * @param toxicityRateDTO the entity to save.
     * @return the persisted entity.
     */
    public ToxicityRateDTO save(ToxicityRateDTO toxicityRateDTO) {
        log.debug("Request to save ToxicityRate : {}", toxicityRateDTO);
        ToxicityRate toxicityRate = toxicityRateMapper.toEntity(toxicityRateDTO);
        toxicityRate = toxicityRateRepository.save(toxicityRate);
        return toxicityRateMapper.toDto(toxicityRate);
    }

    /**
     * Partially update a toxicityRate.
     *
     * @param toxicityRateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ToxicityRateDTO> partialUpdate(ToxicityRateDTO toxicityRateDTO) {
        log.debug("Request to partially update ToxicityRate : {}", toxicityRateDTO);

        return toxicityRateRepository
            .findById(toxicityRateDTO.getId())
            .map(
                existingToxicityRate -> {
                    if (toxicityRateDTO.getName() != null) {
                        existingToxicityRate.setName(toxicityRateDTO.getName());
                    }

                    if (toxicityRateDTO.getDescription() != null) {
                        existingToxicityRate.setDescription(toxicityRateDTO.getDescription());
                    }

                    if (toxicityRateDTO.getNotice() != null) {
                        existingToxicityRate.setNotice(toxicityRateDTO.getNotice());
                    }

                    if (toxicityRateDTO.getAutonomousIntervention() != null) {
                        existingToxicityRate.setAutonomousIntervention(toxicityRateDTO.getAutonomousIntervention());
                    }

                    if (toxicityRateDTO.getInterdependentIntervention() != null) {
                        existingToxicityRate.setInterdependentIntervention(toxicityRateDTO.getInterdependentIntervention());
                    }

                    if (toxicityRateDTO.getSelfManagement() != null) {
                        existingToxicityRate.setSelfManagement(toxicityRateDTO.getSelfManagement());
                    }

                    return existingToxicityRate;
                }
            )
            .map(toxicityRateRepository::save)
            .map(toxicityRateMapper::toDto);
    }

    /**
     * Get all the toxicityRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ToxicityRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ToxicityRates");
        return toxicityRateRepository.findAll(pageable).map(toxicityRateMapper::toDto);
    }

    /**
     * Get one toxicityRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ToxicityRateDTO> findOne(Long id) {
        log.debug("Request to get ToxicityRate : {}", id);
        return toxicityRateRepository.findById(id).map(toxicityRateMapper::toDto);
    }

    /**
     * Delete the toxicityRate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ToxicityRate : {}", id);
        toxicityRateRepository.deleteById(id);
    }
}

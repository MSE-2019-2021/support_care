package uc.dei.mse.supportivecare.service;

import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.repository.TherapeuticRegimeRepository;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;
import uc.dei.mse.supportivecare.service.mapper.TherapeuticRegimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TherapeuticRegime}.
 */
@Service
@Transactional
public class TherapeuticRegimeService {

    private final Logger log = LoggerFactory.getLogger(TherapeuticRegimeService.class);

    private final TherapeuticRegimeRepository therapeuticRegimeRepository;

    private final TherapeuticRegimeMapper therapeuticRegimeMapper;

    public TherapeuticRegimeService(TherapeuticRegimeRepository therapeuticRegimeRepository, TherapeuticRegimeMapper therapeuticRegimeMapper) {
        this.therapeuticRegimeRepository = therapeuticRegimeRepository;
        this.therapeuticRegimeMapper = therapeuticRegimeMapper;
    }

    /**
     * Save a therapeuticRegime.
     *
     * @param therapeuticRegimeDTO the entity to save.
     * @return the persisted entity.
     */
    public TherapeuticRegimeDTO save(TherapeuticRegimeDTO therapeuticRegimeDTO) {
        log.debug("Request to save TherapeuticRegime : {}", therapeuticRegimeDTO);
        TherapeuticRegime therapeuticRegime = therapeuticRegimeMapper.toEntity(therapeuticRegimeDTO);
        therapeuticRegime = therapeuticRegimeRepository.save(therapeuticRegime);
        return therapeuticRegimeMapper.toDto(therapeuticRegime);
    }

    /**
     * Get all the therapeuticRegimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TherapeuticRegimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TherapeuticRegimes");
        return therapeuticRegimeRepository.findAll(pageable)
            .map(therapeuticRegimeMapper::toDto);
    }


    /**
     * Get one therapeuticRegime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TherapeuticRegimeDTO> findOne(Long id) {
        log.debug("Request to get TherapeuticRegime : {}", id);
        return therapeuticRegimeRepository.findById(id)
            .map(therapeuticRegimeMapper::toDto);
    }

    /**
     * Delete the therapeuticRegime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TherapeuticRegime : {}", id);
        therapeuticRegimeRepository.deleteById(id);
    }
}

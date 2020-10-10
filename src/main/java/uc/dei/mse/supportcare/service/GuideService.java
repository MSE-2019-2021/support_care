package uc.dei.mse.supportcare.service;

import uc.dei.mse.supportcare.domain.Guide;
import uc.dei.mse.supportcare.repository.GuideRepository;
import uc.dei.mse.supportcare.service.dto.GuideDTO;
import uc.dei.mse.supportcare.service.mapper.GuideMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Guide}.
 */
@Service
@Transactional
public class GuideService {

    private final Logger log = LoggerFactory.getLogger(GuideService.class);

    private final GuideRepository guideRepository;

    private final GuideMapper guideMapper;

    public GuideService(GuideRepository guideRepository, GuideMapper guideMapper) {
        this.guideRepository = guideRepository;
        this.guideMapper = guideMapper;
    }

    /**
     * Save a guide.
     *
     * @param guideDTO the entity to save.
     * @return the persisted entity.
     */
    public GuideDTO save(GuideDTO guideDTO) {
        log.debug("Request to save Guide : {}", guideDTO);
        Guide guide = guideMapper.toEntity(guideDTO);
        guide = guideRepository.save(guide);
        return guideMapper.toDto(guide);
    }

    /**
     * Get all the guides.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GuideDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Guides");
        return guideRepository.findAll(pageable)
            .map(guideMapper::toDto);
    }


    /**
     * Get one guide by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GuideDTO> findOne(Long id) {
        log.debug("Request to get Guide : {}", id);
        return guideRepository.findById(id)
            .map(guideMapper::toDto);
    }

    /**
     * Delete the guide by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Guide : {}", id);
        guideRepository.deleteById(id);
    }
}

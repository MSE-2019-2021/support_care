package uc.dei.mse.supportivecare.service;

import uc.dei.mse.supportivecare.domain.Treatment;
import uc.dei.mse.supportivecare.repository.TreatmentRepository;
import uc.dei.mse.supportivecare.service.dto.TreatmentDTO;
import uc.dei.mse.supportivecare.service.mapper.TreatmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Treatment}.
 */
@Service
@Transactional
public class TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentService.class);

    private final TreatmentRepository treatmentRepository;

    private final TreatmentMapper treatmentMapper;

    public TreatmentService(TreatmentRepository treatmentRepository, TreatmentMapper treatmentMapper) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
    }

    /**
     * Save a treatment.
     *
     * @param treatmentDTO the entity to save.
     * @return the persisted entity.
     */
    public TreatmentDTO save(TreatmentDTO treatmentDTO) {
        log.debug("Request to save Treatment : {}", treatmentDTO);
        Treatment treatment = treatmentMapper.toEntity(treatmentDTO);
        treatment = treatmentRepository.save(treatment);
        return treatmentMapper.toDto(treatment);
    }

    /**
     * Get all the treatments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Treatments");
        return treatmentRepository.findAll(pageable)
            .map(treatmentMapper::toDto);
    }


    /**
     * Get one treatment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentDTO> findOne(Long id) {
        log.debug("Request to get Treatment : {}", id);
        return treatmentRepository.findById(id)
            .map(treatmentMapper::toDto);
    }

    /**
     * Delete the treatment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Treatment : {}", id);
        treatmentRepository.deleteById(id);
    }
}

package uc.dei.mse.supportivecare.service;

import uc.dei.mse.supportivecare.domain.Diagnostic;
import uc.dei.mse.supportivecare.repository.DiagnosticRepository;
import uc.dei.mse.supportivecare.service.dto.DiagnosticDTO;
import uc.dei.mse.supportivecare.service.mapper.DiagnosticMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Diagnostic}.
 */
@Service
@Transactional
public class DiagnosticService {

    private final Logger log = LoggerFactory.getLogger(DiagnosticService.class);

    private final DiagnosticRepository diagnosticRepository;

    private final DiagnosticMapper diagnosticMapper;

    public DiagnosticService(DiagnosticRepository diagnosticRepository, DiagnosticMapper diagnosticMapper) {
        this.diagnosticRepository = diagnosticRepository;
        this.diagnosticMapper = diagnosticMapper;
    }

    /**
     * Save a diagnostic.
     *
     * @param diagnosticDTO the entity to save.
     * @return the persisted entity.
     */
    public DiagnosticDTO save(DiagnosticDTO diagnosticDTO) {
        log.debug("Request to save Diagnostic : {}", diagnosticDTO);
        Diagnostic diagnostic = diagnosticMapper.toEntity(diagnosticDTO);
        diagnostic = diagnosticRepository.save(diagnostic);
        return diagnosticMapper.toDto(diagnostic);
    }

    /**
     * Get all the diagnostics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DiagnosticDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diagnostics");
        return diagnosticRepository.findAll(pageable)
            .map(diagnosticMapper::toDto);
    }


    /**
     * Get one diagnostic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiagnosticDTO> findOne(Long id) {
        log.debug("Request to get Diagnostic : {}", id);
        return diagnosticRepository.findById(id)
            .map(diagnosticMapper::toDto);
    }

    /**
     * Delete the diagnostic by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Diagnostic : {}", id);
        diagnosticRepository.deleteById(id);
    }
}

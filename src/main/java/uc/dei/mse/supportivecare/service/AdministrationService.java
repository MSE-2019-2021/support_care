package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Administration;
import uc.dei.mse.supportivecare.repository.AdministrationRepository;
import uc.dei.mse.supportivecare.service.dto.AdministrationDTO;
import uc.dei.mse.supportivecare.service.mapper.AdministrationMapper;

/**
 * Service Implementation for managing {@link Administration}.
 */
@Service
@Transactional
public class AdministrationService {

    private final Logger log = LoggerFactory.getLogger(AdministrationService.class);

    private final AdministrationRepository administrationRepository;

    private final AdministrationMapper administrationMapper;

    public AdministrationService(AdministrationRepository administrationRepository, AdministrationMapper administrationMapper) {
        this.administrationRepository = administrationRepository;
        this.administrationMapper = administrationMapper;
    }

    /**
     * Save a administration.
     *
     * @param administrationDTO the entity to save.
     * @return the persisted entity.
     */
    public AdministrationDTO save(AdministrationDTO administrationDTO) {
        log.debug("Request to save Administration : {}", administrationDTO);
        Administration administration = administrationMapper.toEntity(administrationDTO);
        administration = administrationRepository.save(administration);
        return administrationMapper.toDto(administration);
    }

    /**
     * Partially update a administration.
     *
     * @param administrationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdministrationDTO> partialUpdate(AdministrationDTO administrationDTO) {
        log.debug("Request to partially update Administration : {}", administrationDTO);

        return administrationRepository
            .findById(administrationDTO.getId())
            .map(
                existingAdministration -> {
                    if (administrationDTO.getType() != null) {
                        existingAdministration.setType(administrationDTO.getType());
                    }

                    return existingAdministration;
                }
            )
            .map(administrationRepository::save)
            .map(administrationMapper::toDto);
    }

    /**
     * Get all the administrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdministrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Administrations");
        return administrationRepository.findAll(pageable).map(administrationMapper::toDto);
    }

    /**
     * Get one administration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdministrationDTO> findOne(Long id) {
        log.debug("Request to get Administration : {}", id);
        return administrationRepository.findById(id).map(administrationMapper::toDto);
    }

    /**
     * Delete the administration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Administration : {}", id);
        administrationRepository.deleteById(id);
    }
}

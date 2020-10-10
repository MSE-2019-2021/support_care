package uc.dei.mse.supportcare.service;

import uc.dei.mse.supportcare.domain.Drug;
import uc.dei.mse.supportcare.repository.DrugRepository;
import uc.dei.mse.supportcare.service.dto.DrugDTO;
import uc.dei.mse.supportcare.service.mapper.DrugMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Drug}.
 */
@Service
@Transactional
public class DrugService {

    private final Logger log = LoggerFactory.getLogger(DrugService.class);

    private final DrugRepository drugRepository;

    private final DrugMapper drugMapper;

    public DrugService(DrugRepository drugRepository, DrugMapper drugMapper) {
        this.drugRepository = drugRepository;
        this.drugMapper = drugMapper;
    }

    /**
     * Save a drug.
     *
     * @param drugDTO the entity to save.
     * @return the persisted entity.
     */
    public DrugDTO save(DrugDTO drugDTO) {
        log.debug("Request to save Drug : {}", drugDTO);
        Drug drug = drugMapper.toEntity(drugDTO);
        drug = drugRepository.save(drug);
        return drugMapper.toDto(drug);
    }

    /**
     * Get all the drugs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DrugDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drugs");
        return drugRepository.findAll(pageable)
            .map(drugMapper::toDto);
    }


    /**
     * Get one drug by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DrugDTO> findOne(Long id) {
        log.debug("Request to get Drug : {}", id);
        return drugRepository.findById(id)
            .map(drugMapper::toDto);
    }

    /**
     * Delete the drug by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Drug : {}", id);
        drugRepository.deleteById(id);
    }
}

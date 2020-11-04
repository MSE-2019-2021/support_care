package uc.dei.mse.supportivecare.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import uc.dei.mse.supportivecare.domain.Administration;
import uc.dei.mse.supportivecare.domain.*; // for static metamodels
import uc.dei.mse.supportivecare.repository.AdministrationRepository;
import uc.dei.mse.supportivecare.service.dto.AdministrationCriteria;
import uc.dei.mse.supportivecare.service.dto.AdministrationDTO;
import uc.dei.mse.supportivecare.service.mapper.AdministrationMapper;

/**
 * Service for executing complex queries for {@link Administration} entities in the database.
 * The main input is a {@link AdministrationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdministrationDTO} or a {@link Page} of {@link AdministrationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdministrationQueryService extends QueryService<Administration> {

    private final Logger log = LoggerFactory.getLogger(AdministrationQueryService.class);

    private final AdministrationRepository administrationRepository;

    private final AdministrationMapper administrationMapper;

    public AdministrationQueryService(AdministrationRepository administrationRepository, AdministrationMapper administrationMapper) {
        this.administrationRepository = administrationRepository;
        this.administrationMapper = administrationMapper;
    }

    /**
     * Return a {@link List} of {@link AdministrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdministrationDTO> findByCriteria(AdministrationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Administration> specification = createSpecification(criteria);
        return administrationMapper.toDto(administrationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdministrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdministrationDTO> findByCriteria(AdministrationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Administration> specification = createSpecification(criteria);
        return administrationRepository.findAll(specification, page)
            .map(administrationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdministrationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Administration> specification = createSpecification(criteria);
        return administrationRepository.count(specification);
    }

    /**
     * Function to convert {@link AdministrationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Administration> createSpecification(AdministrationCriteria criteria) {
        Specification<Administration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Administration_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Administration_.type));
            }
            if (criteria.getDrugId() != null) {
                specification = specification.and(buildSpecification(criteria.getDrugId(),
                    root -> root.join(Administration_.drugs, JoinType.LEFT).get(Drug_.id)));
            }
        }
        return specification;
    }
}

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

import uc.dei.mse.supportivecare.domain.Treatment;
import uc.dei.mse.supportivecare.domain.*; // for static metamodels
import uc.dei.mse.supportivecare.repository.TreatmentRepository;
import uc.dei.mse.supportivecare.service.dto.TreatmentCriteria;
import uc.dei.mse.supportivecare.service.dto.TreatmentDTO;
import uc.dei.mse.supportivecare.service.mapper.TreatmentMapper;

/**
 * Service for executing complex queries for {@link Treatment} entities in the database.
 * The main input is a {@link TreatmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TreatmentDTO} or a {@link Page} of {@link TreatmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TreatmentQueryService extends QueryService<Treatment> {

    private final Logger log = LoggerFactory.getLogger(TreatmentQueryService.class);

    private final TreatmentRepository treatmentRepository;

    private final TreatmentMapper treatmentMapper;

    public TreatmentQueryService(TreatmentRepository treatmentRepository, TreatmentMapper treatmentMapper) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
    }

    /**
     * Return a {@link List} of {@link TreatmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TreatmentDTO> findByCriteria(TreatmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Treatment> specification = createSpecification(criteria);
        return treatmentMapper.toDto(treatmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TreatmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentDTO> findByCriteria(TreatmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Treatment> specification = createSpecification(criteria);
        return treatmentRepository.findAll(specification, page)
            .map(treatmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TreatmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Treatment> specification = createSpecification(criteria);
        return treatmentRepository.count(specification);
    }

    /**
     * Function to convert {@link TreatmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Treatment> createSpecification(TreatmentCriteria criteria) {
        Specification<Treatment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Treatment_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Treatment_.type));
            }
            if (criteria.getTherapeuticRegimeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTherapeuticRegimeId(),
                    root -> root.join(Treatment_.therapeuticRegimes, JoinType.LEFT).get(TherapeuticRegime_.id)));
            }
        }
        return specification;
    }
}

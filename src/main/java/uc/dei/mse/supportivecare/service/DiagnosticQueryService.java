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

import uc.dei.mse.supportivecare.domain.Diagnostic;
import uc.dei.mse.supportivecare.domain.*; // for static metamodels
import uc.dei.mse.supportivecare.repository.DiagnosticRepository;
import uc.dei.mse.supportivecare.service.dto.DiagnosticCriteria;
import uc.dei.mse.supportivecare.service.dto.DiagnosticDTO;
import uc.dei.mse.supportivecare.service.mapper.DiagnosticMapper;

/**
 * Service for executing complex queries for {@link Diagnostic} entities in the database.
 * The main input is a {@link DiagnosticCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiagnosticDTO} or a {@link Page} of {@link DiagnosticDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiagnosticQueryService extends QueryService<Diagnostic> {

    private final Logger log = LoggerFactory.getLogger(DiagnosticQueryService.class);

    private final DiagnosticRepository diagnosticRepository;

    private final DiagnosticMapper diagnosticMapper;

    public DiagnosticQueryService(DiagnosticRepository diagnosticRepository, DiagnosticMapper diagnosticMapper) {
        this.diagnosticRepository = diagnosticRepository;
        this.diagnosticMapper = diagnosticMapper;
    }

    /**
     * Return a {@link List} of {@link DiagnosticDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiagnosticDTO> findByCriteria(DiagnosticCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Diagnostic> specification = createSpecification(criteria);
        return diagnosticMapper.toDto(diagnosticRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiagnosticDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiagnosticDTO> findByCriteria(DiagnosticCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Diagnostic> specification = createSpecification(criteria);
        return diagnosticRepository.findAll(specification, page)
            .map(diagnosticMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiagnosticCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Diagnostic> specification = createSpecification(criteria);
        return diagnosticRepository.count(specification);
    }

    /**
     * Function to convert {@link DiagnosticCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Diagnostic> createSpecification(DiagnosticCriteria criteria) {
        Specification<Diagnostic> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Diagnostic_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Diagnostic_.name));
            }
            if (criteria.getNotice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotice(), Diagnostic_.notice));
            }
            if (criteria.getTherapeuticRegimeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTherapeuticRegimeId(),
                    root -> root.join(Diagnostic_.therapeuticRegimes, JoinType.LEFT).get(TherapeuticRegime_.id)));
            }
        }
        return specification;
    }
}

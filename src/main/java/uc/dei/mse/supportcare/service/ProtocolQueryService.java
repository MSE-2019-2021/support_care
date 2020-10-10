package uc.dei.mse.supportcare.service;

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

import uc.dei.mse.supportcare.domain.Protocol;
import uc.dei.mse.supportcare.domain.*; // for static metamodels
import uc.dei.mse.supportcare.repository.ProtocolRepository;
import uc.dei.mse.supportcare.service.dto.ProtocolCriteria;
import uc.dei.mse.supportcare.service.dto.ProtocolDTO;
import uc.dei.mse.supportcare.service.mapper.ProtocolMapper;

/**
 * Service for executing complex queries for {@link Protocol} entities in the database.
 * The main input is a {@link ProtocolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProtocolDTO} or a {@link Page} of {@link ProtocolDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProtocolQueryService extends QueryService<Protocol> {

    private final Logger log = LoggerFactory.getLogger(ProtocolQueryService.class);

    private final ProtocolRepository protocolRepository;

    private final ProtocolMapper protocolMapper;

    public ProtocolQueryService(ProtocolRepository protocolRepository, ProtocolMapper protocolMapper) {
        this.protocolRepository = protocolRepository;
        this.protocolMapper = protocolMapper;
    }

    /**
     * Return a {@link List} of {@link ProtocolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProtocolDTO> findByCriteria(ProtocolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Protocol> specification = createSpecification(criteria);
        return protocolMapper.toDto(protocolRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProtocolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProtocolDTO> findByCriteria(ProtocolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Protocol> specification = createSpecification(criteria);
        return protocolRepository.findAll(specification, page)
            .map(protocolMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProtocolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Protocol> specification = createSpecification(criteria);
        return protocolRepository.count(specification);
    }

    /**
     * Function to convert {@link ProtocolCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Protocol> createSpecification(ProtocolCriteria criteria) {
        Specification<Protocol> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Protocol_.id));
            }
            if (criteria.getToxicityDiagnosis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToxicityDiagnosis(), Protocol_.toxicityDiagnosis));
            }
            if (criteria.getCreateUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreateUser(), Protocol_.createUser));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), Protocol_.createDate));
            }
            if (criteria.getUpdateUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdateUser(), Protocol_.updateUser));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Protocol_.updateDate));
            }
            if (criteria.getTherapeuticRegimeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTherapeuticRegimeId(),
                    root -> root.join(Protocol_.therapeuticRegime, JoinType.LEFT).get(TherapeuticRegime_.id)));
            }
            if (criteria.getOutcomeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOutcomeId(),
                    root -> root.join(Protocol_.outcome, JoinType.LEFT).get(Outcome_.id)));
            }
            if (criteria.getGuideId() != null) {
                specification = specification.and(buildSpecification(criteria.getGuideId(),
                    root -> root.join(Protocol_.guide, JoinType.LEFT).get(Guide_.id)));
            }
        }
        return specification;
    }
}

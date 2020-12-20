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
import tech.jhipster.service.QueryService;
import uc.dei.mse.supportivecare.domain.*; // for static metamodels
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.repository.TherapeuticRegimeRepository;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeCriteria;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;
import uc.dei.mse.supportivecare.service.mapper.TherapeuticRegimeMapper;

/**
 * Service for executing complex queries for {@link TherapeuticRegime} entities in the database.
 * The main input is a {@link TherapeuticRegimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TherapeuticRegimeDTO} or a {@link Page} of {@link TherapeuticRegimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TherapeuticRegimeQueryService extends QueryService<TherapeuticRegime> {

    private final Logger log = LoggerFactory.getLogger(TherapeuticRegimeQueryService.class);

    private final TherapeuticRegimeRepository therapeuticRegimeRepository;

    private final TherapeuticRegimeMapper therapeuticRegimeMapper;

    public TherapeuticRegimeQueryService(
        TherapeuticRegimeRepository therapeuticRegimeRepository,
        TherapeuticRegimeMapper therapeuticRegimeMapper
    ) {
        this.therapeuticRegimeRepository = therapeuticRegimeRepository;
        this.therapeuticRegimeMapper = therapeuticRegimeMapper;
    }

    /**
     * Return a {@link List} of {@link TherapeuticRegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TherapeuticRegimeDTO> findByCriteria(TherapeuticRegimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TherapeuticRegime> specification = createSpecification(criteria);
        return therapeuticRegimeMapper.toDto(therapeuticRegimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TherapeuticRegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TherapeuticRegimeDTO> findByCriteria(TherapeuticRegimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TherapeuticRegime> specification = createSpecification(criteria);
        return therapeuticRegimeRepository.findAll(specification, page).map(therapeuticRegimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TherapeuticRegimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TherapeuticRegime> specification = createSpecification(criteria);
        return therapeuticRegimeRepository.count(specification);
    }

    /**
     * Function to convert {@link TherapeuticRegimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TherapeuticRegime> createSpecification(TherapeuticRegimeCriteria criteria) {
        Specification<TherapeuticRegime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TherapeuticRegime_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TherapeuticRegime_.name));
            }
            if (criteria.getAcronym() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcronym(), TherapeuticRegime_.acronym));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), TherapeuticRegime_.purpose));
            }
            if (criteria.getCondition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCondition(), TherapeuticRegime_.condition));
            }
            if (criteria.getTiming() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTiming(), TherapeuticRegime_.timing));
            }
            if (criteria.getIndication() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndication(), TherapeuticRegime_.indication));
            }
            if (criteria.getCriteria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCriteria(), TherapeuticRegime_.criteria));
            }
            if (criteria.getNotice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotice(), TherapeuticRegime_.notice));
            }
            if (criteria.getActiveSubstanceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getActiveSubstanceId(),
                            root -> root.join(TherapeuticRegime_.activeSubstances, JoinType.LEFT).get(ActiveSubstance_.id)
                        )
                    );
            }
            if (criteria.getTreatmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTreatmentId(),
                            root -> root.join(TherapeuticRegime_.treatment, JoinType.LEFT).get(Treatment_.id)
                        )
                    );
            }
            if (criteria.getSymptomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSymptomId(),
                            root -> root.join(TherapeuticRegime_.symptoms, JoinType.LEFT).get(Symptom_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

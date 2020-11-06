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
import uc.dei.mse.supportivecare.domain.ToxicityRate;
import uc.dei.mse.supportivecare.repository.ToxicityRateRepository;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateCriteria;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateDTO;
import uc.dei.mse.supportivecare.service.mapper.ToxicityRateMapper;

/**
 * Service for executing complex queries for {@link ToxicityRate} entities in the database.
 * The main input is a {@link ToxicityRateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ToxicityRateDTO} or a {@link Page} of {@link ToxicityRateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ToxicityRateQueryService extends QueryService<ToxicityRate> {

    private final Logger log = LoggerFactory.getLogger(ToxicityRateQueryService.class);

    private final ToxicityRateRepository toxicityRateRepository;

    private final ToxicityRateMapper toxicityRateMapper;

    public ToxicityRateQueryService(ToxicityRateRepository toxicityRateRepository, ToxicityRateMapper toxicityRateMapper) {
        this.toxicityRateRepository = toxicityRateRepository;
        this.toxicityRateMapper = toxicityRateMapper;
    }

    /**
     * Return a {@link List} of {@link ToxicityRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ToxicityRateDTO> findByCriteria(ToxicityRateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ToxicityRate> specification = createSpecification(criteria);
        return toxicityRateMapper.toDto(toxicityRateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ToxicityRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ToxicityRateDTO> findByCriteria(ToxicityRateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ToxicityRate> specification = createSpecification(criteria);
        return toxicityRateRepository.findAll(specification, page).map(toxicityRateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ToxicityRateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ToxicityRate> specification = createSpecification(criteria);
        return toxicityRateRepository.count(specification);
    }

    /**
     * Function to convert {@link ToxicityRateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ToxicityRate> createSpecification(ToxicityRateCriteria criteria) {
        Specification<ToxicityRate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ToxicityRate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ToxicityRate_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ToxicityRate_.description));
            }
            if (criteria.getNotice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotice(), ToxicityRate_.notice));
            }
            if (criteria.getAutonomousIntervention() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAutonomousIntervention(), ToxicityRate_.autonomousIntervention));
            }
            if (criteria.getInterdependentIntervention() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getInterdependentIntervention(), ToxicityRate_.interdependentIntervention)
                    );
            }
            if (criteria.getSelfManagement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSelfManagement(), ToxicityRate_.selfManagement));
            }
            if (criteria.getSymptomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSymptomId(),
                            root -> root.join(ToxicityRate_.symptoms, JoinType.LEFT).get(Symptom_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

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
import uc.dei.mse.supportivecare.domain.Outcome;
import uc.dei.mse.supportivecare.repository.OutcomeRepository;
import uc.dei.mse.supportivecare.service.dto.OutcomeCriteria;
import uc.dei.mse.supportivecare.service.dto.OutcomeDTO;
import uc.dei.mse.supportivecare.service.mapper.OutcomeMapper;

/**
 * Service for executing complex queries for {@link Outcome} entities in the database.
 * The main input is a {@link OutcomeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OutcomeDTO} or a {@link Page} of {@link OutcomeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OutcomeQueryService extends QueryService<Outcome> {

    private final Logger log = LoggerFactory.getLogger(OutcomeQueryService.class);

    private final OutcomeRepository outcomeRepository;

    private final OutcomeMapper outcomeMapper;

    public OutcomeQueryService(OutcomeRepository outcomeRepository, OutcomeMapper outcomeMapper) {
        this.outcomeRepository = outcomeRepository;
        this.outcomeMapper = outcomeMapper;
    }

    /**
     * Return a {@link List} of {@link OutcomeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OutcomeDTO> findByCriteria(OutcomeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Outcome> specification = createSpecification(criteria);
        return outcomeMapper.toDto(outcomeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OutcomeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OutcomeDTO> findByCriteria(OutcomeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Outcome> specification = createSpecification(criteria);
        return outcomeRepository.findAll(specification, page).map(outcomeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OutcomeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Outcome> specification = createSpecification(criteria);
        return outcomeRepository.count(specification);
    }

    /**
     * Function to convert {@link OutcomeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Outcome> createSpecification(OutcomeCriteria criteria) {
        Specification<Outcome> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Outcome_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Outcome_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Outcome_.description));
            }
            if (criteria.getDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDocumentId(), root -> root.join(Outcome_.documents, JoinType.LEFT).get(Document_.id))
                    );
            }
            if (criteria.getSymptomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSymptomId(), root -> root.join(Outcome_.symptoms, JoinType.LEFT).get(Symptom_.id))
                    );
            }
        }
        return specification;
    }
}

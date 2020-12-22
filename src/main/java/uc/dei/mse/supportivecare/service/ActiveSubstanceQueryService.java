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
import uc.dei.mse.supportivecare.domain.ActiveSubstance;
import uc.dei.mse.supportivecare.repository.ActiveSubstanceRepository;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceCriteria;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceDTO;
import uc.dei.mse.supportivecare.service.mapper.ActiveSubstanceMapper;

/**
 * Service for executing complex queries for {@link ActiveSubstance} entities in the database.
 * The main input is a {@link ActiveSubstanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActiveSubstanceDTO} or a {@link Page} of {@link ActiveSubstanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActiveSubstanceQueryService extends QueryService<ActiveSubstance> {

    private final Logger log = LoggerFactory.getLogger(ActiveSubstanceQueryService.class);

    private final ActiveSubstanceRepository activeSubstanceRepository;

    private final ActiveSubstanceMapper activeSubstanceMapper;

    public ActiveSubstanceQueryService(ActiveSubstanceRepository activeSubstanceRepository, ActiveSubstanceMapper activeSubstanceMapper) {
        this.activeSubstanceRepository = activeSubstanceRepository;
        this.activeSubstanceMapper = activeSubstanceMapper;
    }

    /**
     * Return a {@link List} of {@link ActiveSubstanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActiveSubstanceDTO> findByCriteria(ActiveSubstanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActiveSubstance> specification = createSpecification(criteria);
        return activeSubstanceMapper.toDto(activeSubstanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ActiveSubstanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActiveSubstanceDTO> findByCriteria(ActiveSubstanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActiveSubstance> specification = createSpecification(criteria);
        return activeSubstanceRepository.findAll(specification, page).map(activeSubstanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActiveSubstanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActiveSubstance> specification = createSpecification(criteria);
        return activeSubstanceRepository.count(specification);
    }

    /**
     * Function to convert {@link ActiveSubstanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActiveSubstance> createSpecification(ActiveSubstanceCriteria criteria) {
        Specification<ActiveSubstance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ActiveSubstance_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ActiveSubstance_.name));
            }
            if (criteria.getDosage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDosage(), ActiveSubstance_.dosage));
            }
            if (criteria.getForm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getForm(), ActiveSubstance_.form));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ActiveSubstance_.description));
            }
            if (criteria.getNoticeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNoticeId(),
                            root -> root.join(ActiveSubstance_.notices, JoinType.LEFT).get(Notice_.id)
                        )
                    );
            }
            if (criteria.getAdministrationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAdministrationId(),
                            root -> root.join(ActiveSubstance_.administration, JoinType.LEFT).get(Administration_.id)
                        )
                    );
            }
            if (criteria.getTherapeuticRegimeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTherapeuticRegimeId(),
                            root -> root.join(ActiveSubstance_.therapeuticRegimes, JoinType.LEFT).get(TherapeuticRegime_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

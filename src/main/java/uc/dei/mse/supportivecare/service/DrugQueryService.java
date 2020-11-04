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
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.domain.*; // for static metamodels
import uc.dei.mse.supportivecare.domain.Drug;
import uc.dei.mse.supportivecare.repository.DrugRepository;
import uc.dei.mse.supportivecare.service.dto.DrugCriteria;
import uc.dei.mse.supportivecare.service.dto.DrugDTO;
import uc.dei.mse.supportivecare.service.mapper.DrugMapper;

/**
 * Service for executing complex queries for {@link Drug} entities in the database.
 * The main input is a {@link DrugCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DrugDTO} or a {@link Page} of {@link DrugDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
@GeneratedByJHipster
public class DrugQueryService extends QueryService<Drug> {

    private final Logger log = LoggerFactory.getLogger(DrugQueryService.class);

    private final DrugRepository drugRepository;

    private final DrugMapper drugMapper;

    public DrugQueryService(DrugRepository drugRepository, DrugMapper drugMapper) {
        this.drugRepository = drugRepository;
        this.drugMapper = drugMapper;
    }

    /**
     * Return a {@link List} of {@link DrugDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DrugDTO> findByCriteria(DrugCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Drug> specification = createSpecification(criteria);
        return drugMapper.toDto(drugRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DrugDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DrugDTO> findByCriteria(DrugCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Drug> specification = createSpecification(criteria);
        return drugRepository.findAll(specification, page).map(drugMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DrugCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Drug> specification = createSpecification(criteria);
        return drugRepository.count(specification);
    }

    /**
     * Function to convert {@link DrugCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Drug> createSpecification(DrugCriteria criteria) {
        Specification<Drug> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Drug_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Drug_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Drug_.description));
            }
            if (criteria.getNoticeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNoticeId(), root -> root.join(Drug_.notices, JoinType.LEFT).get(Notice_.id))
                    );
            }
            if (criteria.getAdministrationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAdministrationId(),
                            root -> root.join(Drug_.administration, JoinType.LEFT).get(Administration_.id)
                        )
                    );
            }
            if (criteria.getTherapeuticRegimeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTherapeuticRegimeId(),
                            root -> root.join(Drug_.therapeuticRegimes, JoinType.LEFT).get(TherapeuticRegime_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

package uc.dei.mse.supportivecare.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import uc.dei.mse.supportivecare.domain.*; // for static metamodels
import uc.dei.mse.supportivecare.domain.Thumb;
import uc.dei.mse.supportivecare.repository.ThumbRepository;
import uc.dei.mse.supportivecare.service.dto.ThumbCriteria;
import uc.dei.mse.supportivecare.service.dto.ThumbDTO;
import uc.dei.mse.supportivecare.service.mapper.ThumbMapper;

/**
 * Service for executing complex queries for {@link Thumb} entities in the database.
 * The main input is a {@link ThumbCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ThumbDTO} or a {@link Page} of {@link ThumbDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ThumbQueryService extends QueryService<Thumb> {

    private final Logger log = LoggerFactory.getLogger(ThumbQueryService.class);

    private final ThumbRepository thumbRepository;

    private final ThumbMapper thumbMapper;

    public ThumbQueryService(ThumbRepository thumbRepository, ThumbMapper thumbMapper) {
        this.thumbRepository = thumbRepository;
        this.thumbMapper = thumbMapper;
    }

    /**
     * Return a {@link List} of {@link ThumbDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ThumbDTO> findByCriteria(ThumbCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Thumb> specification = createSpecification(criteria);
        return thumbMapper.toDto(thumbRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ThumbDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ThumbDTO> findByCriteria(ThumbCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Thumb> specification = createSpecification(criteria);
        return thumbRepository.findAll(specification, page).map(thumbMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ThumbCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Thumb> specification = createSpecification(criteria);
        return thumbRepository.count(specification);
    }

    /**
     * Function to convert {@link ThumbCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Thumb> createSpecification(ThumbCriteria criteria) {
        Specification<Thumb> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Thumb_.id));
            }
            if (criteria.getEntityType() != null) {
                specification = specification.and(buildSpecification(criteria.getEntityType(), Thumb_.entityType));
            }
            if (criteria.getEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntityId(), Thumb_.entityId));
            }
            if (criteria.getThumb() != null) {
                specification = specification.and(buildSpecification(criteria.getThumb(), Thumb_.thumb));
            }
        }
        return specification;
    }
}

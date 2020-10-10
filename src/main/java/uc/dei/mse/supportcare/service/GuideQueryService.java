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

import uc.dei.mse.supportcare.domain.Guide;
import uc.dei.mse.supportcare.domain.*; // for static metamodels
import uc.dei.mse.supportcare.repository.GuideRepository;
import uc.dei.mse.supportcare.service.dto.GuideCriteria;
import uc.dei.mse.supportcare.service.dto.GuideDTO;
import uc.dei.mse.supportcare.service.mapper.GuideMapper;

/**
 * Service for executing complex queries for {@link Guide} entities in the database.
 * The main input is a {@link GuideCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GuideDTO} or a {@link Page} of {@link GuideDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GuideQueryService extends QueryService<Guide> {

    private final Logger log = LoggerFactory.getLogger(GuideQueryService.class);

    private final GuideRepository guideRepository;

    private final GuideMapper guideMapper;

    public GuideQueryService(GuideRepository guideRepository, GuideMapper guideMapper) {
        this.guideRepository = guideRepository;
        this.guideMapper = guideMapper;
    }

    /**
     * Return a {@link List} of {@link GuideDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GuideDTO> findByCriteria(GuideCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Guide> specification = createSpecification(criteria);
        return guideMapper.toDto(guideRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GuideDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GuideDTO> findByCriteria(GuideCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Guide> specification = createSpecification(criteria);
        return guideRepository.findAll(specification, page)
            .map(guideMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GuideCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Guide> specification = createSpecification(criteria);
        return guideRepository.count(specification);
    }

    /**
     * Function to convert {@link GuideCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Guide> createSpecification(GuideCriteria criteria) {
        Specification<Guide> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Guide_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Guide_.description));
            }
            if (criteria.getCreateUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreateUser(), Guide_.createUser));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), Guide_.createDate));
            }
            if (criteria.getUpdateUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdateUser(), Guide_.updateUser));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Guide_.updateDate));
            }
        }
        return specification;
    }
}

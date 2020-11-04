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
import uc.dei.mse.supportivecare.domain.Notice;
import uc.dei.mse.supportivecare.repository.NoticeRepository;
import uc.dei.mse.supportivecare.service.dto.NoticeCriteria;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;
import uc.dei.mse.supportivecare.service.mapper.NoticeMapper;

/**
 * Service for executing complex queries for {@link Notice} entities in the database.
 * The main input is a {@link NoticeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoticeDTO} or a {@link Page} of {@link NoticeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
@GeneratedByJHipster
public class NoticeQueryService extends QueryService<Notice> {

    private final Logger log = LoggerFactory.getLogger(NoticeQueryService.class);

    private final NoticeRepository noticeRepository;

    private final NoticeMapper noticeMapper;

    public NoticeQueryService(NoticeRepository noticeRepository, NoticeMapper noticeMapper) {
        this.noticeRepository = noticeRepository;
        this.noticeMapper = noticeMapper;
    }

    /**
     * Return a {@link List} of {@link NoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoticeDTO> findByCriteria(NoticeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Notice> specification = createSpecification(criteria);
        return noticeMapper.toDto(noticeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticeDTO> findByCriteria(NoticeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Notice> specification = createSpecification(criteria);
        return noticeRepository.findAll(specification, page).map(noticeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoticeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Notice> specification = createSpecification(criteria);
        return noticeRepository.count(specification);
    }

    /**
     * Function to convert {@link NoticeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Notice> createSpecification(NoticeCriteria criteria) {
        Specification<Notice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Notice_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Notice_.description));
            }
            if (criteria.getEvaluation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvaluation(), Notice_.evaluation));
            }
            if (criteria.getIntervention() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntervention(), Notice_.intervention));
            }
            if (criteria.getDrugId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDrugId(), root -> root.join(Notice_.drug, JoinType.LEFT).get(Drug_.id))
                    );
            }
        }
        return specification;
    }
}

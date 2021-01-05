package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.Feedback;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * Spring Data SQL repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {
    void deleteByEntityNameAndEntityId(EntityFeedback entityFeedback, Long entityId);

    boolean existsByEntityNameAndEntityIdAndCreatedBy(EntityFeedback entityFeedback, Long entityId, String createdBy);

    void deleteByEntityNameAndEntityIdAndCreatedBy(EntityFeedback entityFeedback, Long entityId, String createdBy);
}

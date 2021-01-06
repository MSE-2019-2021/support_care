package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.Feedback;
import uc.dei.mse.supportivecare.domain.Thumb;
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

    @Query(
        nativeQuery = true,
        value = "SELECT up.countThumbUp as countThumbUp, down.countThumbDown as countThumbDown, user.thumb  as thumb FROM " +
        "(SELECT count(*) as countThumbUp FROM Feedback WHERE entity_name = :entityName AND entity_id = :entityId AND thumb = TRUE) up LEFT JOIN " +
        "(SELECT count(*) as countThumbDown FROM Feedback WHERE entity_name = :entityName AND entity_id = :entityId AND thumb = FALSE) down LEFT JOIN " +
        "(SELECT thumb FROM Feedback WHERE entity_name = :entityName AND entity_id = :entityId AND created_by = :createdBy) user"
    )
    Thumb countAllByEntityNameAndEntityIdAndCreatedBy(
        @Param("entityName") String entityName,
        @Param("entityId") Long entityId,
        @Param("createdBy") String createdBy
    );
}

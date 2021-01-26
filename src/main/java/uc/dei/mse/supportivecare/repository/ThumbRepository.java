package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.Thumb;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.domain.projection.ThumbCount;

/**
 * Spring Data SQL repository for the Thumb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThumbRepository extends JpaRepository<Thumb, Long>, JpaSpecificationExecutor<Thumb> {
    void deleteByEntityTypeAndEntityId(EntityFeedback entityFeedback, Long entityId);

    boolean existsByEntityTypeAndEntityIdAndCreatedBy(EntityFeedback entityFeedback, Long entityId, String createdBy);

    void deleteByEntityTypeAndEntityIdAndCreatedBy(EntityFeedback entityFeedback, Long entityId, String createdBy);

    @Query(
        nativeQuery = true,
        value = "SELECT up.countThumbUp as countThumbUp, down.countThumbDown as countThumbDown, createdby.thumb as thumb FROM " +
        "(SELECT count(*) as countThumbUp FROM Thumb WHERE entity_type = :entityType AND entity_id = :entityId AND thumb = TRUE) up LEFT JOIN " +
        "(SELECT count(*) as countThumbDown FROM Thumb WHERE entity_type = :entityType AND entity_id = :entityId AND thumb = FALSE) down ON TRUE LEFT JOIN " +
        "(SELECT thumb FROM Thumb WHERE entity_type = :entityType AND entity_id = :entityId AND created_by = :createdBy) createdby ON TRUE"
    )
    ThumbCount countAllByEntityTypeAndEntityIdAndCreatedBy(
        @Param("entityType") String entityType,
        @Param("entityId") Long entityId,
        @Param("createdBy") String createdBy
    );
}

package uc.dei.mse.supportcare.repository;

import uc.dei.mse.supportcare.domain.Guide;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Guide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuideRepository extends JpaRepository<Guide, Long>, JpaSpecificationExecutor<Guide> {
}

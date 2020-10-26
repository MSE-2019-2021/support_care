package uc.dei.mse.supportivecare.repository;

import uc.dei.mse.supportivecare.domain.Notice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Notice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {
}

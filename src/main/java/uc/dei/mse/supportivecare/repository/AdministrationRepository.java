package uc.dei.mse.supportivecare.repository;

import uc.dei.mse.supportivecare.domain.Administration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Administration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministrationRepository extends JpaRepository<Administration, Long>, JpaSpecificationExecutor<Administration> {
}

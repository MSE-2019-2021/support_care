package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.domain.Administration;

/**
 * Spring Data SQL repository for the Administration entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface AdministrationRepository extends JpaRepository<Administration, Long>, JpaSpecificationExecutor<Administration> {}

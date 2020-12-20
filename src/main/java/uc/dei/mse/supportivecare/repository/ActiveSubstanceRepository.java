package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.ActiveSubstance;

/**
 * Spring Data SQL repository for the ActiveSubstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiveSubstanceRepository extends JpaRepository<ActiveSubstance, Long>, JpaSpecificationExecutor<ActiveSubstance> {}

package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.ToxicityRate;

/**
 * Spring Data SQL repository for the ToxicityRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToxicityRateRepository extends JpaRepository<ToxicityRate, Long>, JpaSpecificationExecutor<ToxicityRate> {}

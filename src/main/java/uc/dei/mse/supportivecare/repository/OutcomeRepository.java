package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.Outcome;

/**
 * Spring Data SQL repository for the Outcome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long>, JpaSpecificationExecutor<Outcome> {}

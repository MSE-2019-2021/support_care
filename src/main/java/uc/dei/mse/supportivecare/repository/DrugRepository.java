package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.domain.Drug;

/**
 * Spring Data SQL repository for the Drug entity.
 */
@SuppressWarnings("unused")
@Repository
@GeneratedByJHipster
public interface DrugRepository extends JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug> {}

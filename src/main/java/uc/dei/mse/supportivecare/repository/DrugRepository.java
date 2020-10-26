package uc.dei.mse.supportivecare.repository;

import uc.dei.mse.supportivecare.domain.Drug;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Drug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug> {
}

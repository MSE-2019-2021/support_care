package uc.dei.mse.supportivecare.repository;

import uc.dei.mse.supportivecare.domain.TherapeuticRegime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TherapeuticRegime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TherapeuticRegimeRepository extends JpaRepository<TherapeuticRegime, Long>, JpaSpecificationExecutor<TherapeuticRegime> {
}

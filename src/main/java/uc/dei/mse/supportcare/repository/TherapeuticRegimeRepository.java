package uc.dei.mse.supportcare.repository;

import uc.dei.mse.supportcare.domain.TherapeuticRegime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TherapeuticRegime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TherapeuticRegimeRepository extends JpaRepository<TherapeuticRegime, Long>, JpaSpecificationExecutor<TherapeuticRegime> {
}

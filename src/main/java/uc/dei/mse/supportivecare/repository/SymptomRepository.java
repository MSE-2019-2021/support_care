package uc.dei.mse.supportivecare.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.Symptom;

/**
 * Spring Data SQL repository for the Symptom entity.
 */
@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long>, JpaSpecificationExecutor<Symptom> {
    @Query(
        value = "select distinct symptom from Symptom symptom left join fetch symptom.therapeuticRegimes left join fetch symptom.outcomes left join fetch symptom.toxicityRates",
        countQuery = "select count(distinct symptom) from Symptom symptom"
    )
    Page<Symptom> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct symptom from Symptom symptom left join fetch symptom.therapeuticRegimes left join fetch symptom.outcomes left join fetch symptom.toxicityRates"
    )
    List<Symptom> findAllWithEagerRelationships();

    @Query(
        "select symptom from Symptom symptom left join fetch symptom.therapeuticRegimes left join fetch symptom.outcomes left join fetch symptom.toxicityRates where symptom.id =:id"
    )
    Optional<Symptom> findOneWithEagerRelationships(@Param("id") Long id);
}

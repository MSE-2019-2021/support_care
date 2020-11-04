package uc.dei.mse.supportivecare.repository;

import uc.dei.mse.supportivecare.domain.TherapeuticRegime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TherapeuticRegime entity.
 */
@Repository
public interface TherapeuticRegimeRepository extends JpaRepository<TherapeuticRegime, Long>, JpaSpecificationExecutor<TherapeuticRegime> {

    @Query(value = "select distinct therapeuticRegime from TherapeuticRegime therapeuticRegime left join fetch therapeuticRegime.drugs",
        countQuery = "select count(distinct therapeuticRegime) from TherapeuticRegime therapeuticRegime")
    Page<TherapeuticRegime> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct therapeuticRegime from TherapeuticRegime therapeuticRegime left join fetch therapeuticRegime.drugs")
    List<TherapeuticRegime> findAllWithEagerRelationships();

    @Query("select therapeuticRegime from TherapeuticRegime therapeuticRegime left join fetch therapeuticRegime.drugs where therapeuticRegime.id =:id")
    Optional<TherapeuticRegime> findOneWithEagerRelationships(@Param("id") Long id);
}

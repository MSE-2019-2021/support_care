package uc.dei.mse.supportivecare.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uc.dei.mse.supportivecare.domain.Drug;

/**
 * Spring Data SQL repository for the Drug entity.
 */
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug> {
    @Query(
        value = "select distinct drug from Drug drug left join fetch drug.notices",
        countQuery = "select count(distinct drug) from Drug drug"
    )
    Page<Drug> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct drug from Drug drug left join fetch drug.notices")
    List<Drug> findAllWithEagerRelationships();

    @Query("select drug from Drug drug left join fetch drug.notices where drug.id =:id")
    Optional<Drug> findOneWithEagerRelationships(@Param("id") Long id);
}

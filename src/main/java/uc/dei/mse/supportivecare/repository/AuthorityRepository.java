package uc.dei.mse.supportivecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uc.dei.mse.supportivecare.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}

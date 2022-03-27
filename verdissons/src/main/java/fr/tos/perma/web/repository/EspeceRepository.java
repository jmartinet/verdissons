package fr.tos.perma.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import fr.tos.perma.web.domain.Espece;

/**
 * Spring Data SQL repository for the Espece entity.
 */
@Repository
public interface EspeceRepository extends JpaRepository<Espece, Long>, JpaSpecificationExecutor<Espece> {
}

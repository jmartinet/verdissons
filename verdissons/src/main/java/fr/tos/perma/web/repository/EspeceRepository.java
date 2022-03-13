package fr.tos.perma.web.repository;

import fr.tos.perma.web.domain.Espece;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Espece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspeceRepository extends JpaRepository<Espece, Integer>, JpaSpecificationExecutor<Espece> {}

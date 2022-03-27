package fr.tos.perma.web.repository;

import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.domain.Genre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Famille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilleRepository extends JpaRepository<Famille, Long>, JpaSpecificationExecutor<Famille> {}

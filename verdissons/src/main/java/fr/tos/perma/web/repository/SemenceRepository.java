package fr.tos.perma.web.repository;

import fr.tos.perma.web.domain.Semence;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Semence entity.
 */
@Repository
public interface SemenceRepository extends JpaRepository<Semence, Long> {
    default Optional<Semence> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Semence> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Semence> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct semence from Semence semence left join fetch semence.variete",
        countQuery = "select count(distinct semence) from Semence semence"
    )
    Page<Semence> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct semence from Semence semence left join fetch semence.variete")
    List<Semence> findAllWithToOneRelationships();

    @Query("select semence from Semence semence left join fetch semence.variete where semence.id =:id")
    Optional<Semence> findOneWithToOneRelationships(@Param("id") Long id);
}

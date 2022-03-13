package fr.tos.perma.web.repository;

import fr.tos.perma.web.domain.Variete;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Variete entity.
 */
@Repository
public interface VarieteRepository extends JpaRepository<Variete, Long> {
    default Optional<Variete> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Variete> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Variete> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct variete from Variete variete left join fetch variete.espece",
        countQuery = "select count(distinct variete) from Variete variete"
    )
    Page<Variete> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct variete from Variete variete left join fetch variete.espece")
    List<Variete> findAllWithToOneRelationships();

    @Query("select variete from Variete variete left join fetch variete.espece where variete.id =:id")
    Optional<Variete> findOneWithToOneRelationships(@Param("id") Long id);
}

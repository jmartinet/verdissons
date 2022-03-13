package fr.tos.perma.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.tos.perma.web.domain.Genre;

/**
 * Spring Data SQL repository for the Genre entity.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>, JpaSpecificationExecutor<Genre> {
	default Optional<Genre> findOneWithEagerRelationships(Integer id) {
		return this.findOneWithToOneRelationships(id);
	}

	default List<Genre> findAllWithEagerRelationships() {
		return this.findAllWithToOneRelationships();
	}

	default Page<Genre> findAllWithEagerRelationships(Pageable pageable) {
		return this.findAllWithToOneRelationships(pageable);
	}

	@Query(value = "select distinct genre from Genre genre left join fetch genre.parent", countQuery = "select count(distinct genre) from Genre genre")
	Page<Genre> findAllWithToOneRelationships(Pageable pageable);

	@Query("select distinct genre from Genre genre left join fetch genre.parent")
	List<Genre> findAllWithToOneRelationships();

	@Query("select genre from Genre genre left join fetch genre.parent where genre.id =:id")
	Optional<Genre> findOneWithToOneRelationships(@Param("id") Integer id);
}

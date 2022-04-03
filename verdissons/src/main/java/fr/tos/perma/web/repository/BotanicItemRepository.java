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

import fr.tos.perma.web.domain.BotanicItem;

/**
 * Spring Data SQL repository for the BotanicItem entity.
 */
@Repository
public interface BotanicItemRepository<T extends BotanicItem> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
	default Optional<T> findOneWithEagerRelationships(Long id) {
		return this.findOneWithToOneRelationships(id);
	}

	default List<T> findAllWithEagerRelationships() {
		return this.findAllWithToOneRelationships();
	}

	default Page<T> findAllWithEagerRelationships(Pageable pageable) {
		return this.findAllWithToOneRelationships(pageable);
	}

	@Query(value = "select distinct bi from #{#entityName} bi left join fetch bi.parent", countQuery = "select count(distinct bi) from #{#entityName} bi")
	Page<T> findAllWithToOneRelationships(Pageable pageable);

	@Query("select distinct bi from #{#entityName} bi left join fetch bi.parent")
	List<T> findAllWithToOneRelationships();

	@Query("select bi from #{#entityName} bi left join fetch bi.parent where bi.id =:id")
	Optional<T> findOneWithToOneRelationships(@Param("id") Long id);
}

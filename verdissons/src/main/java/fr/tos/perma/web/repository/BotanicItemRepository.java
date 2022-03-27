package fr.tos.perma.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import fr.tos.perma.web.domain.BotanicItem;

/**
 * Spring Data SQL repository for the BotanicItem entity.
 */
@Repository
public interface BotanicItemRepository extends JpaRepository<BotanicItem, Long>, JpaSpecificationExecutor<BotanicItem> {

}

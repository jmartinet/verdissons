package fr.tos.perma.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tos.perma.web.repository.BotanicItemRepository;

@Service
@Transactional
public class BotanicItemService {

	private final Logger log = LoggerFactory.getLogger(BotanicItemService.class);

	private final BotanicItemRepository botanicItemRepository;

	public BotanicItemService(BotanicItemRepository botanicItemRepository) {
		this.botanicItemRepository = botanicItemRepository;
	}

}

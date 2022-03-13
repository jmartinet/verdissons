package fr.tos.perma.web.service.mapper;

import org.junit.jupiter.api.BeforeEach;

import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.service.dto.FamilleDTO;

class FamilleMapperTest {

	private BotanicItemMapper<Famille, FamilleDTO> familleMapper;

	@BeforeEach
	public void setUp() {
		familleMapper = new BotanicItemMapper<Famille, FamilleDTO>();
	}
}

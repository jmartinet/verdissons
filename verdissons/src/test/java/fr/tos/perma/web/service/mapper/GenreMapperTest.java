package fr.tos.perma.web.service.mapper;

import org.junit.jupiter.api.BeforeEach;

import fr.tos.perma.web.domain.Genre;
import fr.tos.perma.web.service.dto.GenreDTO;

class GenreMapperTest {

	private BotanicItemMapper<Genre, GenreDTO> genreMapper;

    @BeforeEach
    public void setUp() {
        genreMapper = new BotanicItemMapper<Genre, GenreDTO>();
    }
}

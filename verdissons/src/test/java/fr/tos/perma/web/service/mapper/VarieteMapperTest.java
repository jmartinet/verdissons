package fr.tos.perma.web.service.mapper;

import org.junit.jupiter.api.BeforeEach;

import fr.tos.perma.web.service.mapper.impl.VarieteMapperImpl;

class VarieteMapperTest {

	private VarieteMapper varieteMapper;

	@BeforeEach
	public void setUp() {
		varieteMapper = new VarieteMapperImpl();
	}
}

package fr.tos.perma.web.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.tos.perma.web.service.mapper.impl.SemenceMapperImpl;

class SemenceMapperTest {

    private SemenceMapper semenceMapper;

    @BeforeEach
    public void setUp() {
        semenceMapper = new SemenceMapperImpl();
    }
}

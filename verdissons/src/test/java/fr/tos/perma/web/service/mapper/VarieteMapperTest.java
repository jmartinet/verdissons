package fr.tos.perma.web.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VarieteMapperTest {

    private VarieteMapper varieteMapper;

    @BeforeEach
    public void setUp() {
        varieteMapper = new VarieteMapperImpl();
    }
}

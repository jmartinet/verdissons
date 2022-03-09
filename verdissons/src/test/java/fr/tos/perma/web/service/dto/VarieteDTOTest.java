package fr.tos.perma.web.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tos.perma.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VarieteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VarieteDTO.class);
        VarieteDTO varieteDTO1 = new VarieteDTO();
        varieteDTO1.setId(1L);
        VarieteDTO varieteDTO2 = new VarieteDTO();
        assertThat(varieteDTO1).isNotEqualTo(varieteDTO2);
        varieteDTO2.setId(varieteDTO1.getId());
        assertThat(varieteDTO1).isEqualTo(varieteDTO2);
        varieteDTO2.setId(2L);
        assertThat(varieteDTO1).isNotEqualTo(varieteDTO2);
        varieteDTO1.setId(null);
        assertThat(varieteDTO1).isNotEqualTo(varieteDTO2);
    }
}

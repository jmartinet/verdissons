package fr.tos.perma.web.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tos.perma.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SemenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SemenceDTO.class);
        SemenceDTO semenceDTO1 = new SemenceDTO();
        semenceDTO1.setId(1L);
        SemenceDTO semenceDTO2 = new SemenceDTO();
        assertThat(semenceDTO1).isNotEqualTo(semenceDTO2);
        semenceDTO2.setId(semenceDTO1.getId());
        assertThat(semenceDTO1).isEqualTo(semenceDTO2);
        semenceDTO2.setId(2L);
        assertThat(semenceDTO1).isNotEqualTo(semenceDTO2);
        semenceDTO1.setId(null);
        assertThat(semenceDTO1).isNotEqualTo(semenceDTO2);
    }
}

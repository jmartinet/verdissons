package fr.tos.perma.web.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tos.perma.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspeceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspeceDTO.class);
        EspeceDTO especeDTO1 = new EspeceDTO();
        especeDTO1.setId(1L);
        EspeceDTO especeDTO2 = new EspeceDTO();
        assertThat(especeDTO1).isNotEqualTo(especeDTO2);
        especeDTO2.setId(especeDTO1.getId());
        assertThat(especeDTO1).isEqualTo(especeDTO2);
        especeDTO2.setId(2L);
        assertThat(especeDTO1).isNotEqualTo(especeDTO2);
        especeDTO1.setId(null);
        assertThat(especeDTO1).isNotEqualTo(especeDTO2);
    }
}

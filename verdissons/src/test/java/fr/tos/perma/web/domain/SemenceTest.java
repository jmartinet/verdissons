package fr.tos.perma.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tos.perma.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SemenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Semence.class);
        Semence semence1 = new Semence();
        semence1.setId(1L);
        Semence semence2 = new Semence();
        semence2.setId(semence1.getId());
        assertThat(semence1).isEqualTo(semence2);
        semence2.setId(2L);
        assertThat(semence1).isNotEqualTo(semence2);
        semence1.setId(null);
        assertThat(semence1).isNotEqualTo(semence2);
    }
}

package fr.tos.perma.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.tos.perma.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BotanicItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BotanicItem.class);
        BotanicItem botanicItem1 = new BotanicItem();
        botanicItem1.setId(1L);
        BotanicItem botanicItem2 = new BotanicItem();
        botanicItem2.setId(botanicItem1.getId());
        assertThat(botanicItem1).isEqualTo(botanicItem2);
        botanicItem2.setId(2L);
        assertThat(botanicItem1).isNotEqualTo(botanicItem2);
        botanicItem1.setId(null);
        assertThat(botanicItem1).isNotEqualTo(botanicItem2);
    }
}

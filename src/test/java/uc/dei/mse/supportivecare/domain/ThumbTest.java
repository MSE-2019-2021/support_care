package uc.dei.mse.supportivecare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ThumbTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thumb.class);
        Thumb thumb1 = new Thumb();
        thumb1.setId(1L);
        Thumb thumb2 = new Thumb();
        thumb2.setId(thumb1.getId());
        assertThat(thumb1).isEqualTo(thumb2);
        thumb2.setId(2L);
        assertThat(thumb1).isNotEqualTo(thumb2);
        thumb1.setId(null);
        assertThat(thumb1).isNotEqualTo(thumb2);
    }
}

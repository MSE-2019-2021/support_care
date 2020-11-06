package uc.dei.mse.supportivecare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ToxicityRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ToxicityRate.class);
        ToxicityRate toxicityRate1 = new ToxicityRate();
        toxicityRate1.setId(1L);
        ToxicityRate toxicityRate2 = new ToxicityRate();
        toxicityRate2.setId(toxicityRate1.getId());
        assertThat(toxicityRate1).isEqualTo(toxicityRate2);
        toxicityRate2.setId(2L);
        assertThat(toxicityRate1).isNotEqualTo(toxicityRate2);
        toxicityRate1.setId(null);
        assertThat(toxicityRate1).isNotEqualTo(toxicityRate2);
    }
}

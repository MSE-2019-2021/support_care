package uc.dei.mse.supportivecare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ActiveSubstanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiveSubstance.class);
        ActiveSubstance activeSubstance1 = new ActiveSubstance();
        activeSubstance1.setId(1L);
        ActiveSubstance activeSubstance2 = new ActiveSubstance();
        activeSubstance2.setId(activeSubstance1.getId());
        assertThat(activeSubstance1).isEqualTo(activeSubstance2);
        activeSubstance2.setId(2L);
        assertThat(activeSubstance1).isNotEqualTo(activeSubstance2);
        activeSubstance1.setId(null);
        assertThat(activeSubstance1).isNotEqualTo(activeSubstance2);
    }
}

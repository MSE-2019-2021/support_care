package uc.dei.mse.supportivecare.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

public class TherapeuticRegimeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TherapeuticRegime.class);
        TherapeuticRegime therapeuticRegime1 = new TherapeuticRegime();
        therapeuticRegime1.setId(1L);
        TherapeuticRegime therapeuticRegime2 = new TherapeuticRegime();
        therapeuticRegime2.setId(therapeuticRegime1.getId());
        assertThat(therapeuticRegime1).isEqualTo(therapeuticRegime2);
        therapeuticRegime2.setId(2L);
        assertThat(therapeuticRegime1).isNotEqualTo(therapeuticRegime2);
        therapeuticRegime1.setId(null);
        assertThat(therapeuticRegime1).isNotEqualTo(therapeuticRegime2);
    }
}

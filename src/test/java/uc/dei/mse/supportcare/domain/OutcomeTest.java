package uc.dei.mse.supportcare.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportcare.web.rest.TestUtil;

public class OutcomeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outcome.class);
        Outcome outcome1 = new Outcome();
        outcome1.setId(1L);
        Outcome outcome2 = new Outcome();
        outcome2.setId(outcome1.getId());
        assertThat(outcome1).isEqualTo(outcome2);
        outcome2.setId(2L);
        assertThat(outcome1).isNotEqualTo(outcome2);
        outcome1.setId(null);
        assertThat(outcome1).isNotEqualTo(outcome2);
    }
}

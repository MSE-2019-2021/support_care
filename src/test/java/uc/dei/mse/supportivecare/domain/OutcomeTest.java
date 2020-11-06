package uc.dei.mse.supportivecare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class OutcomeTest {

    @Test
    void equalsVerifier() throws Exception {
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

package uc.dei.mse.supportivecare.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

public class AdministrationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Administration.class);
        Administration administration1 = new Administration();
        administration1.setId(1L);
        Administration administration2 = new Administration();
        administration2.setId(administration1.getId());
        assertThat(administration1).isEqualTo(administration2);
        administration2.setId(2L);
        assertThat(administration1).isNotEqualTo(administration2);
        administration1.setId(null);
        assertThat(administration1).isNotEqualTo(administration2);
    }
}

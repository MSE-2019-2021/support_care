package uc.dei.mse.supportcare.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportcare.web.rest.TestUtil;

public class GuideTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guide.class);
        Guide guide1 = new Guide();
        guide1.setId(1L);
        Guide guide2 = new Guide();
        guide2.setId(guide1.getId());
        assertThat(guide1).isEqualTo(guide2);
        guide2.setId(2L);
        assertThat(guide1).isNotEqualTo(guide2);
        guide1.setId(null);
        assertThat(guide1).isNotEqualTo(guide2);
    }
}

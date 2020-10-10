package uc.dei.mse.supportcare.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportcare.web.rest.TestUtil;

public class GuideDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuideDTO.class);
        GuideDTO guideDTO1 = new GuideDTO();
        guideDTO1.setId(1L);
        GuideDTO guideDTO2 = new GuideDTO();
        assertThat(guideDTO1).isNotEqualTo(guideDTO2);
        guideDTO2.setId(guideDTO1.getId());
        assertThat(guideDTO1).isEqualTo(guideDTO2);
        guideDTO2.setId(2L);
        assertThat(guideDTO1).isNotEqualTo(guideDTO2);
        guideDTO1.setId(null);
        assertThat(guideDTO1).isNotEqualTo(guideDTO2);
    }
}

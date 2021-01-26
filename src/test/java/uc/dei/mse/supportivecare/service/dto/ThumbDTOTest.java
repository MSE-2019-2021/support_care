package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ThumbDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThumbDTO.class);
        ThumbDTO thumbDTO1 = new ThumbDTO();
        thumbDTO1.setId(1L);
        ThumbDTO thumbDTO2 = new ThumbDTO();
        assertThat(thumbDTO1).isNotEqualTo(thumbDTO2);
        thumbDTO2.setId(thumbDTO1.getId());
        assertThat(thumbDTO1).isEqualTo(thumbDTO2);
        thumbDTO2.setId(2L);
        assertThat(thumbDTO1).isNotEqualTo(thumbDTO2);
        thumbDTO1.setId(null);
        assertThat(thumbDTO1).isNotEqualTo(thumbDTO2);
    }
}

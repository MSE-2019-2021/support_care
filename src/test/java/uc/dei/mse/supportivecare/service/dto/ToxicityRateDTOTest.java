package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ToxicityRateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ToxicityRateDTO.class);
        ToxicityRateDTO toxicityRateDTO1 = new ToxicityRateDTO();
        toxicityRateDTO1.setId(1L);
        ToxicityRateDTO toxicityRateDTO2 = new ToxicityRateDTO();
        assertThat(toxicityRateDTO1).isNotEqualTo(toxicityRateDTO2);
        toxicityRateDTO2.setId(toxicityRateDTO1.getId());
        assertThat(toxicityRateDTO1).isEqualTo(toxicityRateDTO2);
        toxicityRateDTO2.setId(2L);
        assertThat(toxicityRateDTO1).isNotEqualTo(toxicityRateDTO2);
        toxicityRateDTO1.setId(null);
        assertThat(toxicityRateDTO1).isNotEqualTo(toxicityRateDTO2);
    }
}

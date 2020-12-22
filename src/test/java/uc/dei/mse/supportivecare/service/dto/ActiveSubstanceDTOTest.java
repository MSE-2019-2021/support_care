package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ActiveSubstanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiveSubstanceDTO.class);
        ActiveSubstanceDTO activeSubstanceDTO1 = new ActiveSubstanceDTO();
        activeSubstanceDTO1.setId(1L);
        ActiveSubstanceDTO activeSubstanceDTO2 = new ActiveSubstanceDTO();
        assertThat(activeSubstanceDTO1).isNotEqualTo(activeSubstanceDTO2);
        activeSubstanceDTO2.setId(activeSubstanceDTO1.getId());
        assertThat(activeSubstanceDTO1).isEqualTo(activeSubstanceDTO2);
        activeSubstanceDTO2.setId(2L);
        assertThat(activeSubstanceDTO1).isNotEqualTo(activeSubstanceDTO2);
        activeSubstanceDTO1.setId(null);
        assertThat(activeSubstanceDTO1).isNotEqualTo(activeSubstanceDTO2);
    }
}

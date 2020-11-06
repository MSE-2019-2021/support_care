package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class TherapeuticRegimeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TherapeuticRegimeDTO.class);
        TherapeuticRegimeDTO therapeuticRegimeDTO1 = new TherapeuticRegimeDTO();
        therapeuticRegimeDTO1.setId(1L);
        TherapeuticRegimeDTO therapeuticRegimeDTO2 = new TherapeuticRegimeDTO();
        assertThat(therapeuticRegimeDTO1).isNotEqualTo(therapeuticRegimeDTO2);
        therapeuticRegimeDTO2.setId(therapeuticRegimeDTO1.getId());
        assertThat(therapeuticRegimeDTO1).isEqualTo(therapeuticRegimeDTO2);
        therapeuticRegimeDTO2.setId(2L);
        assertThat(therapeuticRegimeDTO1).isNotEqualTo(therapeuticRegimeDTO2);
        therapeuticRegimeDTO1.setId(null);
        assertThat(therapeuticRegimeDTO1).isNotEqualTo(therapeuticRegimeDTO2);
    }
}

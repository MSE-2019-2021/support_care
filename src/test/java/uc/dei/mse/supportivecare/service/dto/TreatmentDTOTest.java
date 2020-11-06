package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class TreatmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentDTO.class);
        TreatmentDTO treatmentDTO1 = new TreatmentDTO();
        treatmentDTO1.setId(1L);
        TreatmentDTO treatmentDTO2 = new TreatmentDTO();
        assertThat(treatmentDTO1).isNotEqualTo(treatmentDTO2);
        treatmentDTO2.setId(treatmentDTO1.getId());
        assertThat(treatmentDTO1).isEqualTo(treatmentDTO2);
        treatmentDTO2.setId(2L);
        assertThat(treatmentDTO1).isNotEqualTo(treatmentDTO2);
        treatmentDTO1.setId(null);
        assertThat(treatmentDTO1).isNotEqualTo(treatmentDTO2);
    }
}

package uc.dei.mse.supportivecare.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

public class TreatmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
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

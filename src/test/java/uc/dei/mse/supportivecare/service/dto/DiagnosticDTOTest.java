package uc.dei.mse.supportivecare.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

public class DiagnosticDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosticDTO.class);
        DiagnosticDTO diagnosticDTO1 = new DiagnosticDTO();
        diagnosticDTO1.setId(1L);
        DiagnosticDTO diagnosticDTO2 = new DiagnosticDTO();
        assertThat(diagnosticDTO1).isNotEqualTo(diagnosticDTO2);
        diagnosticDTO2.setId(diagnosticDTO1.getId());
        assertThat(diagnosticDTO1).isEqualTo(diagnosticDTO2);
        diagnosticDTO2.setId(2L);
        assertThat(diagnosticDTO1).isNotEqualTo(diagnosticDTO2);
        diagnosticDTO1.setId(null);
        assertThat(diagnosticDTO1).isNotEqualTo(diagnosticDTO2);
    }
}

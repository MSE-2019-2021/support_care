package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

@GeneratedByJHipster
class AdministrationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrationDTO.class);
        AdministrationDTO administrationDTO1 = new AdministrationDTO();
        administrationDTO1.setId(1L);
        AdministrationDTO administrationDTO2 = new AdministrationDTO();
        assertThat(administrationDTO1).isNotEqualTo(administrationDTO2);
        administrationDTO2.setId(administrationDTO1.getId());
        assertThat(administrationDTO1).isEqualTo(administrationDTO2);
        administrationDTO2.setId(2L);
        assertThat(administrationDTO1).isNotEqualTo(administrationDTO2);
        administrationDTO1.setId(null);
        assertThat(administrationDTO1).isNotEqualTo(administrationDTO2);
    }
}

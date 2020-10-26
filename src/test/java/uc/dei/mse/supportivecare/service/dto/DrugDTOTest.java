package uc.dei.mse.supportivecare.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

public class DrugDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugDTO.class);
        DrugDTO drugDTO1 = new DrugDTO();
        drugDTO1.setId(1L);
        DrugDTO drugDTO2 = new DrugDTO();
        assertThat(drugDTO1).isNotEqualTo(drugDTO2);
        drugDTO2.setId(drugDTO1.getId());
        assertThat(drugDTO1).isEqualTo(drugDTO2);
        drugDTO2.setId(2L);
        assertThat(drugDTO1).isNotEqualTo(drugDTO2);
        drugDTO1.setId(null);
        assertThat(drugDTO1).isNotEqualTo(drugDTO2);
    }
}

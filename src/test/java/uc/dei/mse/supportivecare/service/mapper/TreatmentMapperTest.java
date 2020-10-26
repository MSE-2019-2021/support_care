package uc.dei.mse.supportivecare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TreatmentMapperTest {

    private TreatmentMapper treatmentMapper;

    @BeforeEach
    public void setUp() {
        treatmentMapper = new TreatmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(treatmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(treatmentMapper.fromId(null)).isNull();
    }
}

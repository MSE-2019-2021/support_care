package uc.dei.mse.supportivecare.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreatmentMapperTest {

    private TreatmentMapper treatmentMapper;

    @BeforeEach
    public void setUp() {
        treatmentMapper = new TreatmentMapperImpl();
    }
}

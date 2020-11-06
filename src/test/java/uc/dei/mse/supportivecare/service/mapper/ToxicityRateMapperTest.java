package uc.dei.mse.supportivecare.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToxicityRateMapperTest {

    private ToxicityRateMapper toxicityRateMapper;

    @BeforeEach
    public void setUp() {
        toxicityRateMapper = new ToxicityRateMapperImpl();
    }
}

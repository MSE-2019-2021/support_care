package uc.dei.mse.supportivecare.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActiveSubstanceMapperTest {

    private ActiveSubstanceMapper activeSubstanceMapper;

    @BeforeEach
    public void setUp() {
        activeSubstanceMapper = new ActiveSubstanceMapperImpl();
    }
}

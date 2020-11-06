package uc.dei.mse.supportivecare.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutcomeMapperTest {

    private OutcomeMapper outcomeMapper;

    @BeforeEach
    public void setUp() {
        outcomeMapper = new OutcomeMapperImpl();
    }
}

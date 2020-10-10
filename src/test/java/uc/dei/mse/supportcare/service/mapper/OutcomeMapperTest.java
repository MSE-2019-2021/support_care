package uc.dei.mse.supportcare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OutcomeMapperTest {

    private OutcomeMapper outcomeMapper;

    @BeforeEach
    public void setUp() {
        outcomeMapper = new OutcomeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(outcomeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(outcomeMapper.fromId(null)).isNull();
    }
}

package uc.dei.mse.supportivecare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TherapeuticRegimeMapperTest {

    private TherapeuticRegimeMapper therapeuticRegimeMapper;

    @BeforeEach
    public void setUp() {
        therapeuticRegimeMapper = new TherapeuticRegimeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(therapeuticRegimeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(therapeuticRegimeMapper.fromId(null)).isNull();
    }
}

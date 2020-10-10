package uc.dei.mse.supportcare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GuideMapperTest {

    private GuideMapper guideMapper;

    @BeforeEach
    public void setUp() {
        guideMapper = new GuideMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(guideMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(guideMapper.fromId(null)).isNull();
    }
}

package uc.dei.mse.supportivecare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DrugMapperTest {

    private DrugMapper drugMapper;

    @BeforeEach
    public void setUp() {
        drugMapper = new DrugMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(drugMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(drugMapper.fromId(null)).isNull();
    }
}

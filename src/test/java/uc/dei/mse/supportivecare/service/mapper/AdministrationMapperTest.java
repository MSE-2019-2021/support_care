package uc.dei.mse.supportivecare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdministrationMapperTest {

    private AdministrationMapper administrationMapper;

    @BeforeEach
    public void setUp() {
        administrationMapper = new AdministrationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(administrationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(administrationMapper.fromId(null)).isNull();
    }
}

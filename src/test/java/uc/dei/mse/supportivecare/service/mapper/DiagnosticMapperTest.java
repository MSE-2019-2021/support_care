package uc.dei.mse.supportivecare.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiagnosticMapperTest {

    private DiagnosticMapper diagnosticMapper;

    @BeforeEach
    public void setUp() {
        diagnosticMapper = new DiagnosticMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(diagnosticMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(diagnosticMapper.fromId(null)).isNull();
    }
}

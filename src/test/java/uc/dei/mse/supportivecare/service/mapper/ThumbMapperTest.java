package uc.dei.mse.supportivecare.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThumbMapperTest {

    private ThumbMapper thumbMapper;

    @BeforeEach
    public void setUp() {
        thumbMapper = new ThumbMapperImpl();
    }
}

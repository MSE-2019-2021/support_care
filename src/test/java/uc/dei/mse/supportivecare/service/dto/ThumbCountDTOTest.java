package uc.dei.mse.supportivecare.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class ThumbCountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        ThumbCountDTO domainObject1 = ThumbCountDTO.class.getConstructor().newInstance();
        assertThat(domainObject1.toString()).isNotNull();
        assertThat(domainObject1).isEqualTo(domainObject1);
        assertThat(domainObject1).hasSameHashCodeAs(domainObject1);
        // Test with an instance of another class
        Object testOtherObject = new Object();
        assertThat(domainObject1).isNotEqualTo(testOtherObject);
        assertThat(domainObject1).isNotEqualTo(null);
        // Test with an instance of the same class
        ThumbCountDTO domainObject2 = ThumbCountDTO.class.getConstructor().newInstance();
        assertThat(domainObject1).isEqualTo(domainObject2);
        // HashCodes are equals because the objects are not persisted yet
        assertThat(domainObject1).hasSameHashCodeAs(domainObject2);

        ThumbCountDTO thumbCountDTO1 = new ThumbCountDTO();
        thumbCountDTO1.setCountThumbUp(1L);
        thumbCountDTO1.setCountThumbDown(1L);
        thumbCountDTO1.setThumb(true);
        ThumbCountDTO thumbCountDTO2 = new ThumbCountDTO();
        assertThat(thumbCountDTO1).isNotEqualTo(thumbCountDTO2);
        thumbCountDTO2.setCountThumbUp(thumbCountDTO1.getCountThumbUp());
        thumbCountDTO2.setCountThumbDown(thumbCountDTO1.getCountThumbDown());
        thumbCountDTO2.setThumb(thumbCountDTO1.getThumb());
        assertThat(thumbCountDTO1).isEqualTo(thumbCountDTO2);
        thumbCountDTO2.setCountThumbUp(2L);
        thumbCountDTO2.setCountThumbDown(2L);
        thumbCountDTO2.setThumb(false);
        assertThat(thumbCountDTO1).isNotEqualTo(thumbCountDTO2);
        thumbCountDTO2.setCountThumbUp(null);
        thumbCountDTO2.setCountThumbDown(null);
        thumbCountDTO2.setThumb(null);
        assertThat(thumbCountDTO1).isNotEqualTo(thumbCountDTO2);
    }
}

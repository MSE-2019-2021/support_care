package uc.dei.mse.supportivecare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uc.dei.mse.supportivecare.web.rest.TestUtil;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = new Document();
        document1.setId(1L);
        Document document2 = new Document();
        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);
        document2.setId(2L);
        assertThat(document1).isNotEqualTo(document2);
        document1.setId(null);
        assertThat(document1).isNotEqualTo(document2);
    }
}

package com.sampletracking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sampletracking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LaboratoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Laboratory.class);
        Laboratory laboratory1 = new Laboratory();
        laboratory1.setId("id1");
        Laboratory laboratory2 = new Laboratory();
        laboratory2.setId(laboratory1.getId());
        assertThat(laboratory1).isEqualTo(laboratory2);
        laboratory2.setId("id2");
        assertThat(laboratory1).isNotEqualTo(laboratory2);
        laboratory1.setId(null);
        assertThat(laboratory1).isNotEqualTo(laboratory2);
    }
}

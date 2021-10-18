package com.sampletracking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sampletracking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TestTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestType.class);
        TestType testType1 = new TestType();
        testType1.setId("id1");
        TestType testType2 = new TestType();
        testType2.setId(testType1.getId());
        assertThat(testType1).isEqualTo(testType2);
        testType2.setId("id2");
        assertThat(testType1).isNotEqualTo(testType2);
        testType1.setId(null);
        assertThat(testType1).isNotEqualTo(testType2);
    }
}

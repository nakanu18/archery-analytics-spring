package com.deveradev.archeryanalyticsspring.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class RoundDTOTest {
    @Autowired
    private Validator validator;

    @InjectMocks
    private RoundDTO dto;

    @Test
    public void testEmptyDate() {
        setUpRoundDTO(dto);
        dto.date = "";

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Date is required"));
    }

    @Test
    public void testInvalidArcherId() {
        setUpRoundDTO(dto);
        dto.archerId = 0;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Archer ID must be a positive integer"));
    }

    @Test
    public void testNumberOfEnds_Null() {
        setUpRoundDTO(dto);
        dto.numEnds = null;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Number of ends is required"));
    }

    @Test
    public void testNumberOfEnds_Min() {
        setUpRoundDTO(dto);
        dto.numEnds = 0;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Number of ends must be a positive integer"));
    }

    @Test
    public void testNumberOfArrowsPerEnds_Null() {
        setUpRoundDTO(dto);
        dto.numArrowsPerEnd = null;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Number of arrows per end is required"));
    }

    @Test
    public void testNumberOfArrowsPerEnds_Min() {
        setUpRoundDTO(dto);
        dto.numArrowsPerEnd = 0;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Number of arrows per end must be a positive integer"));
    }

    @Test
    public void testDistance_Null() {
        setUpRoundDTO(dto);
        dto.distM = null;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Distance in meters is required"));
    }

    @Test
    public void testDistance_Min() {
        setUpRoundDTO(dto);
        dto.distM = 0;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Distance must be a positive integer"));
    }

    @Test
    public void testTargetSize_Null() {
        setUpRoundDTO(dto);
        dto.targetSizeCM = null;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Target size in cm is required"));
    }

    @Test
    public void testTargetSize_Min() {
        setUpRoundDTO(dto);
        dto.targetSizeCM = 19;

        Set<ConstraintViolation<RoundDTO>> violations = validator.validate(dto);
        assertThat(violations, hasSize(1));

        ConstraintViolation<RoundDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage(), equalTo("Target size in cm must be 20cm or greater"));
    }

    //
    // Helper methods
    //

    private void setUpRoundDTO(RoundDTO roundDTO) {
        roundDTO.archerId = 1;
        roundDTO.date = "2024-04-24 10:10am";
        roundDTO.numEnds = 10;
        roundDTO.numArrowsPerEnd = 3;
        roundDTO.distM = 18;
        roundDTO.targetSizeCM = 40;
    }
}

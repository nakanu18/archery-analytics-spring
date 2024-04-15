package com.deveradev.archeryanalyticsspring.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoundDTO {
    @NotEmpty(message = "Date is required")
    public String date;

    @Min(value = 1, message = "Archer ID must be a positive integer")
    public Integer archerId;

    @NotNull(message = "Number of ends is required")
    @Min(value = 1, message = "Number of ends must be 1 or greater")
    public Integer numEnds;

    @NotNull(message = "Number of arrows per end is required")
    @Min(value = 1, message = "Number of arrows per end must be 1 or greater")
    public Integer numArrowsPerEnd;

    @NotNull(message = "Distance in meters is required")
    @Min(value = 1, message = "Distance in meters must be 1m or greater")
    public Integer distM;

    @NotNull(message = "Target size in cm is required")
    @Min(value = 20, message = "Target size in cm must be 20cm or greater")
    public Integer targetSizeCM;

    @Override
    public String toString() {
        return "RoundDTO{" +
                "date='" + date + '\'' +
                ", archerId=" + archerId +
                ", numEnds=" + numEnds +
                ", numArrowsPerEnd=" + numArrowsPerEnd +
                ", distM=" + distM +
                ", targetSizeCM=" + targetSizeCM +
                '}';
    }
}

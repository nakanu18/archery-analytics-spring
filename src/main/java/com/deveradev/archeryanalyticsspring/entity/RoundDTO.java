package com.deveradev.archeryanalyticsspring.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class RoundDTO {
    public Integer id;
    @NotEmpty(message = "Date is required")
    public String date;
    @Min(value = 1, message = "Archer ID must be a positive integer")
    public Integer archerId;

    @Override
    public String toString() {
        return "RoundDTO{" +
                "id=" + id +
                ", date=" + date +
                ", archerId=" + archerId +
                '}';
    }
}

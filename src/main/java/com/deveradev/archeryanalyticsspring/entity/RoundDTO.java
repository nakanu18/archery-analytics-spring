package com.deveradev.archeryanalyticsspring.entity;

import java.util.Date;

public class RoundDTO {
    public int id;
    public Date date;
    public int archerId;

    @Override
    public String toString() {
        return "RoundDTO{" +
                "id=" + id +
                ", date=" + date +
                ", archerId=" + archerId +
                '}';
    }
}

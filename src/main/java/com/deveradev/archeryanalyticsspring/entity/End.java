package com.deveradev.archeryanalyticsspring.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "end")
public class End {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "number")
    public int number;

    @Column(name = "values")
    public String values; // e.g. "X X 10 9 8 7 0"

    @ManyToOne
    @JoinColumn(name = "round_id")
    public Round round;

    @Override
    public String toString() {
        return "End{" +
                "id=" + id +
                ", number=" + number +
                ", values='" + values + '\'' +
                ", round=" + round +
                '}';
    }
}

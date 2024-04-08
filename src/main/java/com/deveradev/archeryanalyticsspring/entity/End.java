package com.deveradev.archeryanalyticsspring.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "end")
public class End {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "row")
    public int row;

    @Column(name = "values")
    public String values; // e.g. "X X 10 9 8 7 0"

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "round_id")
    public Round round;

    @Override
    public String toString() {
        return "End{" +
                "id=" + id +
                ", row=" + row +
                ", values='" + values + '\'' +
                ", round=" + round +
                '}';
    }
}

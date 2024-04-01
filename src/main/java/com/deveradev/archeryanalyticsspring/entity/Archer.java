package com.deveradev.archeryanalyticsspring.entity;

import jakarta.persistence.*;

@Entity
@Table(name="archer")
public class Archer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    @Override
    public String toString() {
        return "Archer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

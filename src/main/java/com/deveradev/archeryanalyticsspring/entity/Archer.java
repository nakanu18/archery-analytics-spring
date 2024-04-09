package com.deveradev.archeryanalyticsspring.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="archer")
public class Archer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "archer",
               fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    public List<Round> rounds;

    @Override
    public String toString() {
        return "Archer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rounds=" + rounds +
                '}';
    }
}

package com.deveradev.archeryanalyticsspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "round")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "date")
    public Date date;

    @ManyToOne
    @JoinColumn(name = "archer_id")
    @JsonIgnore
    public Archer archer;

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", date=" + date +
                ", archer=" + archer +
                '}';
    }
}

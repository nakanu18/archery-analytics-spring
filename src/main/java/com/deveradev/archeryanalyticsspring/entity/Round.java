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
    private int id;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "archer_id")
    @JsonIgnore
    private Archer archer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Archer getArcher() {
        return archer;
    }

    public void setArcher(Archer archer) {
        this.archer = archer;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", date=" + date +
                ", archer=" + archer +
                '}';
    }
}

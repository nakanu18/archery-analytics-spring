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

    @Column(name = "num_ends")
    private Integer numEnds;

    @Column(name = "num_arrows_per_end")
    private Integer numArrowsPerEnd;

    @Column(name = "dist_m")
    private Integer distM;

    @Column(name = "target_size_cm")
    private Integer targetSizeCM;

    public Round(RoundDTO dto) {
        this.numEnds = dto.numEnds;
        this.numArrowsPerEnd = dto.numArrowsPerEnd;
        this.distM = dto.distM;
        this.targetSizeCM = dto.targetSizeCM;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getNumEnds() {
        return numEnds;
    }

    public void setNumEnds(Integer numEnds) {
        this.numEnds = numEnds;
    }

    public Integer getNumArrowsPerEnd() {
        return numArrowsPerEnd;
    }

    public void setNumArrowsPerEnd(Integer numArrowsPerEnd) {
        this.numArrowsPerEnd = numArrowsPerEnd;
    }

    public Integer getDistM() {
        return distM;
    }

    public void setDistM(Integer distM) {
        this.distM = distM;
    }

    public Integer getTargetSizeCM() {
        return targetSizeCM;
    }

    public void setTargetSizeCM(Integer targetSizeCM) {
        this.targetSizeCM = targetSizeCM;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", date=" + date +
                ", archer=" + archer +
                ", numEnds=" + numEnds +
                ", numArrowsPerEnd=" + numArrowsPerEnd +
                ", distM=" + distM +
                ", targetSizeCM=" + targetSizeCM +
                '}';
    }
}

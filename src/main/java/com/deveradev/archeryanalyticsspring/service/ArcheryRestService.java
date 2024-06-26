package com.deveradev.archeryanalyticsspring.service;

import com.deveradev.archeryanalyticsspring.dao.ArcherRepository;
import com.deveradev.archeryanalyticsspring.dao.RoundRepository;
import com.deveradev.archeryanalyticsspring.entity.Archer;
import com.deveradev.archeryanalyticsspring.entity.Round;
import com.deveradev.archeryanalyticsspring.rest.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArcheryRestService {
    private ArcherRepository archerRepository;
    private RoundRepository roundRepository;

    @Autowired
    public ArcheryRestService(ArcherRepository archerRepository, RoundRepository roundRepository) {
        this.archerRepository = archerRepository;
        this.roundRepository = roundRepository;
    }

    //
    // Archers
    //

    public List<Archer> findAllArchers() {
        return archerRepository.findAll();
    }

    public Archer findArcherById(int id) {
        Optional<Archer> archer = archerRepository.findById(id);

        if (archer.isEmpty()) {
            throw new NotFoundException("Archer #id not found: " + id);
        }
        return archer.get();
    }

    @Transactional
    public Archer addArcher(Archer archer) {
        return archerRepository.save(archer);
    }

    @Transactional
    public void deleteArcher(int id) {
        Optional<Archer> archer = archerRepository.findById(id);

        if (archer.isEmpty()) {
            throw new NotFoundException("Archer #id not found: " + id);
        }

        for (Round round : archer.get().getRounds()) {
            round.setArcher(null);
            roundRepository.deleteById(round.getId());
        }
        archer.get().setRounds(null);
        archerRepository.deleteById(id);
    }

    //
    // Rounds
    //

    @Transactional
    public Round addRound(Round round) {
        return roundRepository.save(round);
    }

    @Transactional
    public void deleteRound(int roundId) {
        Optional<Round> round = roundRepository.findById(roundId);

        if (round.isEmpty()) {
            throw new NotFoundException("Round #id not found: " + roundId);
        }
        roundRepository.deleteById(roundId);
    }

    public List<Round> findAllRoundsForArcherId(int archerId) {
        return roundRepository.findAllRoundsForArcherId(archerId);
    }


}

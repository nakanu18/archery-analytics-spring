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

    // Archers

    public List<Archer> findAllArchers() {
        return archerRepository.findAll();
    }

    public Archer findArcherById(int id) {
        Optional<Archer> archer = archerRepository.findById(id);

        if (archer.isEmpty()) {
            throw new NotFoundException("Archer id not found: " + id);
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
            throw new NotFoundException("Archer id not found: " + id);
        }
        archerRepository.deleteById(id);
    }

    // Rounds

    @Query("SELECT r FROM Round r WHERE r.archer.id = ?1")
    public List<Round> findAllRoundsForArcherId(int archerId) {
        // No further implementation needed, query is defined in @Query annotation
        return roundRepository.findAll();
    }
}

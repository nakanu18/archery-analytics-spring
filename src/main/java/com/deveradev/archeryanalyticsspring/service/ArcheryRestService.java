package com.deveradev.archeryanalyticsspring.service;

import com.deveradev.archeryanalyticsspring.dao.ArcherRepository;
import com.deveradev.archeryanalyticsspring.entity.Archer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArcheryRestService {
    private ArcherRepository archerRepository;

    @Autowired
    public ArcheryRestService(ArcherRepository archerRepository) {
        this.archerRepository = archerRepository;
    }

    public List<Archer> findAllArchers() {
        return archerRepository.findAll();
    }

    public Optional<Archer> findArcherById(int id) {
        return archerRepository.findById(id);
    }

    @Transactional
    public Archer addArcher(Archer archer) {
        return archerRepository.save(archer);
    }

    @Transactional
    public void deleteArcher(int id) {
        archerRepository.deleteById(id);
    }
}

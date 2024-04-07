package com.deveradev.archeryanalyticsspring.dao;

import com.deveradev.archeryanalyticsspring.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Integer> {
    @Query("SELECT r FROM Round r WHERE r.archer.id = ?1") // Optional for documentation
    List<Round> findAllRoundsForArcherId(int archerId);
}

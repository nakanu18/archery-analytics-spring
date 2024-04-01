package com.deveradev.archeryanalyticsspring.dao;

import com.deveradev.archeryanalyticsspring.entity.Archer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArcherRepository extends JpaRepository<Archer, Integer> {
}

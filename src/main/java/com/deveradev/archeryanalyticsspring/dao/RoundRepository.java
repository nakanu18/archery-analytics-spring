package com.deveradev.archeryanalyticsspring.dao;

import com.deveradev.archeryanalyticsspring.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Integer> {
}

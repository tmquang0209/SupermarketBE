package com.supermarket.backend.Repository;

import com.supermarket.backend.Entity.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Integer> {
    @Query("SELECT SE FROM StatisticsEntity SE")
    StatisticsEntity getFirst();
}

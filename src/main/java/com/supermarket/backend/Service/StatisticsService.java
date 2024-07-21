package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.StatisticsEntity;
import com.supermarket.backend.Repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;

    public StatisticsEntity get(){
        return statisticsRepository.getFirst();
    }
}

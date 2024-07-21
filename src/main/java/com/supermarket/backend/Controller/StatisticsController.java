package com.supermarket.backend.Controller;

import com.supermarket.backend.Service.StatisticsService;
import com.supermarket.backend.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/details")
    public ApiResponse<?> getAll(){
        return new ApiResponse<>(true, statisticsService.get(), "Get statistics successful.");
    }
}

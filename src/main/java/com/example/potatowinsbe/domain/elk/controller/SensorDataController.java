package com.example.potatowinsbe.domain.elk.controller;

import com.example.potatowinsbe.domain.elk.entity.SensorData;
import com.example.potatowinsbe.domain.elk.service.SensorAlgorithmService;
import com.example.potatowinsbe.domain.elk.service.SensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SensorDataController {

    private static final Logger logger = LoggerFactory.getLogger(SensorDataController.class);

    private final SensorDataService sensorDataService;
    private final SensorAlgorithmService sensorAlgorithmService;

    public SensorDataController(SensorDataService sensorDataService, SensorAlgorithmService sensorAlgorithmService) {
        this.sensorDataService = sensorDataService;
        this.sensorAlgorithmService = sensorAlgorithmService;
    }

    @GetMapping("/sensor-data")
    public List<SensorData> getAllSensorData() {
        List<SensorData> allData = sensorDataService.getAllSensorData();
        logger.info("Returned {} records to the client", allData.size());
        return allData;
    }

    @GetMapping("/sensor-data/search")
    public List<SensorData> findByApplicationName(@RequestParam String applicationName) {
        List<SensorData> data = sensorDataService.findByApplicationName(applicationName);
        logger.info("Returned {} records for applicationName '{}'", data.size(), applicationName);
        return data;
    }

    @GetMapping("/sensor-data/abnormal")
    public Map<String, Object> getAbnormalMetrics() {
        List<Map<String, Object>> data = sensorAlgorithmService.monitorAndAdjustMetrics(); // 올바른 메서드 이름 사용
        Map<String, Object> response = new HashMap<>();
        response.put("message", "장치별 최신 데이터 및 이상 항목 처리 결과");
        response.put("data", data);
        return response;
    }
}

package com.lungnod.database.controller;

import com.lungnod.database.model.NoduleSample;
import com.lungnod.database.model.SampleTable;
import com.lungnod.database.model.Stats;
import com.lungnod.database.repository.SampleTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NoduleController {

    @GetMapping("/stats")
    public Stats getStats() {
        return new Stats("500", "----", "68.2%");
    }

    @Autowired
    private SampleTableRepository sampleTableRepository;

    @GetMapping("/samples")
    public List<SampleTable> getSamples(@RequestParam(required = false) String stage) {
        // 逻辑：如果 stage 为空，或者不是 1-4，则查询所有数据
        if (stage == null || stage.isEmpty() || stage.equals("undefined")) {
            System.out.println("查询全量样本数据...");
            return sampleTableRepository.findAll();
        }

        String[] stageNames = {"Healthy", "Solid", "GGO", "Malignant"};
        try {
            int idx = Integer.parseInt(stage) - 1;
            if (idx >= 0 && idx < 4) {
                String currentStageName = stageNames[idx];
                return sampleTableRepository.findByStage(currentStageName);
            }
        } catch (NumberFormatException e) {
            // 参数格式不对也返回所有
        }

        return sampleTableRepository.findAll();
    }

    @GetMapping("/analysis/stats")
    public Map<String, Object> getAnalysisStats() {
        List<SampleTable> allData = sampleTableRepository.findAll();

        // 1. 基础统计
        long totalSubSamples = allData.size();
        long totalIndividuals = allData.stream().map(SampleTable::getIndividualId).distinct().count();

        // 2. 统计各分期的数量
        Map<String, Long> stageDist = allData.stream()
                .collect(Collectors.groupingBy(SampleTable::getStage, Collectors.counting()));

        // 3. 统计各组学类型的数量
        Map<String, Long> omicsDist = allData.stream()
                .collect(Collectors.groupingBy(SampleTable::getOmicsType, Collectors.counting()));

        // 封装返回
        Map<String, Object> response = new HashMap<>();
        response.put("totalIndividuals", totalIndividuals);
        response.put("totalSubSamples", totalSubSamples);
        response.put("stageDist", stageDist);
        response.put("omicsDist", omicsDist);

        return response;
    }
}
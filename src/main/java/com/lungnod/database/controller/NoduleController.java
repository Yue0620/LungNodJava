package com.lungnod.database.controller;

import com.lungnod.database.model.NoduleSample;
import com.lungnod.database.model.SampleTable;
import com.lungnod.database.model.LungNodeSample;
import com.lungnod.database.model.LungData;
import com.lungnod.database.model.Stats;
import com.lungnod.database.repository.SampleTableRepository;
import com.lungnod.database.repository.LungNodeSampleRepository;
import com.lungnod.database.repository.LungDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NoduleController {

    @GetMapping("/stats")
    public Stats getStats() {
        // 样本数：lungnod_sample 表的所有样本
        long sampleCount = lungNodeSampleRepository.count();
        
        // 项目数：lungnod_data 中 distinct 的 myProjectId
        List<LungData> allData = lungDataRepository.findAll();
        long projectCount = allData.stream()
                .map(LungData::getMyProjectId)
                .distinct()
                .count();
        
        // 数据集数：lungnod_data 中 distinct 的 myDatasetId
        long datasetCount = allData.stream()
                .map(LungData::getMyDatasetId)
                .distinct()
                .count();
        
        return new Stats(
            String.format("%,d", sampleCount),
            String.format("%,d", projectCount),
            String.format("%,d", datasetCount)
        );
    }

    @Autowired
    private SampleTableRepository sampleTableRepository;
    @Autowired
    private LungNodeSampleRepository lungNodeSampleRepository;
    @Autowired
    private LungDataRepository lungDataRepository;
    @GetMapping("/samples")
    public List<SampleTable> getSamples(@RequestParam(required = false) String stage) {
        // 逻辑：如果 stage 为空，或者不是 1-4，则查询所有数据
        if (stage == null || stage.isEmpty() || stage.equals("undefined")) {
            // System.out.println("查询全量样本数据...");
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

    @GetMapping("/node-samples")
    public List<LungNodeSample> getNodeSamples(
            @RequestParam(required = false) String stage,
            @RequestParam(required = false) String omicsType,
            @RequestParam(required = false) String disease,
            @RequestParam(required = false) String tissue
    ) {
        // 获取lungnod_sample表数据
        List<LungNodeSample> result = lungNodeSampleRepository.findAll();

        // 按 stage 过滤
        if (stage != null && !stage.isEmpty() && !stage.equals("undefined")) {
            result = result.stream()
                    .filter(d -> stage.equals(d.getStage()))
                    .collect(Collectors.toList());
        }

        // 按 omicsType 过滤
        if (omicsType != null && !omicsType.isEmpty() && !omicsType.equals("undefined")) {
            result = result.stream()
                    .filter(d -> omicsType.equals(d.getOmicsType()))
                    .collect(Collectors.toList());
        }

        // 按 cancerType 过滤（disease 参数映射到 cancerType）
        if (disease != null && !disease.isEmpty() && !disease.equals("undefined")) {
            result = result.stream()
                    .filter(d -> d.getCancerType() != null && d.getCancerType().contains(disease))
                    .collect(Collectors.toList());
        }

        // 按 tissue 过滤
        if (tissue != null && !tissue.isEmpty() && !tissue.equals("undefined")) {
            result = result.stream()
                    .filter(d -> d.getTissue() != null && d.getTissue().contains(tissue))
                    .collect(Collectors.toList());
        }

        return result;
    }

    @GetMapping("/analysis/stats")
    public Map<String, Object> getAnalysisStats() {
        // ===== lungnod_data 统计 =====
        List<LungData> allDatasets = lungDataRepository.findAll();
        
        long totalProjects = allDatasets.stream()
                .map(LungData::getMyProjectId)
                .distinct()
                .count();
        
        long totalDatasets = allDatasets.stream()
                .map(LungData::getMyDatasetId)
                .distinct()
                .count();
        
        // 按组学类型统计数据集
        Map<String, Long> omicsDistData = allDatasets.stream()
                .collect(Collectors.groupingBy(LungData::getOmicsType, Collectors.counting()));

        // ===== lungnod_sample 统计 =====
        List<LungNodeSample> allSamples = lungNodeSampleRepository.findAll();
        
        long totalSamples = allSamples.size();
        long totalIndividuals = allSamples.stream()
                .map(LungNodeSample::getIndividualId)
                .distinct()
                .count();
        
        // 按病理阶段统计
        Map<String, Long> stageDist = allSamples.stream()
                .filter(s -> s.getStage() != null && !s.getStage().equals("NA"))
                .collect(Collectors.groupingBy(LungNodeSample::getStage, Collectors.counting()));
        
        // 按疾病类型统计（规范化大小写）
        Map<String, Long> diseaseDist = allSamples.stream()
                .filter(s -> s.getCancerType() != null && !s.getCancerType().equals("NA"))
                .collect(Collectors.groupingBy(
                        s -> normalizeDiseaseType(s.getCancerType()),
                        Collectors.counting()
                ));
        
        // 按组织统计
        Map<String, Long> tissueDist = allSamples.stream()
                .filter(s -> s.getTissue() != null && !s.getTissue().equals("NA"))
                .collect(Collectors.groupingBy(LungNodeSample::getTissue, Collectors.counting()));
        
        // 按组学类型统计样本
        Map<String, Long> omicsDistSamples = allSamples.stream()
                .filter(s -> s.getOmicsType() != null && !s.getOmicsType().equals("NA"))
                .collect(Collectors.groupingBy(LungNodeSample::getOmicsType, Collectors.counting()));

        // 封装返回
        Map<String, Object> response = new HashMap<>();
        
        // lungnod_data 统计
        response.put("totalProjects", totalProjects);
        response.put("totalDatasets", totalDatasets);
        response.put("omicsDistData", omicsDistData);
        
        // lungnod_sample 统计
        response.put("totalSamples", totalSamples);
        response.put("totalIndividuals", totalIndividuals);
        response.put("stageDist", stageDist);
        response.put("diseaseDist", diseaseDist);
        response.put("tissueDist", tissueDist);
        response.put("omicsDistSamples", omicsDistSamples);

        return response;
    }

    // 规范化疾病类型名称（处理大小写不一致）
    private String normalizeDiseaseType(String diseaseType) {
        if (diseaseType == null) {
            return "NA";
        }
        // 特殊处理：将 "Lung adenocarcinoma" 转换为 "Lung Adenocarcinoma"
        if (diseaseType.equalsIgnoreCase("Lung Adenocarcinoma")) {
            return "Lung Adenocarcinoma";
        }
        return diseaseType;
    }

    @GetMapping("/data")
    public List<LungData> getLungData(
            @RequestParam(required = false) String projectId,
            @RequestParam(required = false) String omicsType,
            @RequestParam(required = false) String disease,
            @RequestParam(required = false) String tissue
    ) {
        List<LungData> result = lungDataRepository.findAll();

        // 按 projectId 过滤
        if (projectId != null && !projectId.isEmpty() && !projectId.equals("undefined")) {
            result = result.stream()
                    .filter(d -> projectId.equals(d.getMyProjectId()))
                    .collect(Collectors.toList());
        }

        // 按 omicsType 过滤
        if (omicsType != null && !omicsType.isEmpty() && !omicsType.equals("undefined")) {
            result = result.stream()
                    .filter(d -> omicsType.equals(d.getOmicsType()))
                    .collect(Collectors.toList());
        }

        // 按 disease 过滤
        if (disease != null && !disease.isEmpty() && !disease.equals("undefined")) {
            result = result.stream()
                    .filter(d -> d.getDisease() != null && d.getDisease().contains(disease))
                    .collect(Collectors.toList());
        }

        // 按 tissue 过滤
        if (tissue != null && !tissue.isEmpty() && !tissue.equals("undefined")) {
            result = result.stream()
                    .filter(d -> d.getTissue() != null && d.getTissue().contains(tissue))
                    .collect(Collectors.toList());
        }

        return result;
    }

    @GetMapping("/data/projects")
    public List<String> getAllProjects() {
        return lungDataRepository.findAll().stream()
                .map(LungData::getMyProjectId)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("/data/detail")
    public ResponseEntity<LungData> getDataDetail(
            @RequestParam String projectId,
            @RequestParam String datasetId
    ) {
        if (projectId == null || projectId.isEmpty() || datasetId == null || datasetId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return lungDataRepository.findById(datasetId)
                .filter(d -> projectId.equals(d.getMyProjectId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

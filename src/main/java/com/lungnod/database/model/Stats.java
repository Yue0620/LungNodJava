package com.lungnod.database.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Stats {
    private String samples;     // 样本数
    private String projects;    // 项目数
    private String datasets;    // 数据集数
}
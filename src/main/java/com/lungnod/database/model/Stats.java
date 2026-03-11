package com.lungnod.database.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Stats {
    private String cases;       // 病例样本
    private String images;      // 标注图像
    private String integrity;   // 数据完整度
}
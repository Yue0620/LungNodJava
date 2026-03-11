package com.lungnod.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "samples")
public class NoduleSample {
    @Id
    private String sampleId;
    private String stage;
    private String source;
    private String x1; // 结节直径
    private String x2; // CT值
    private String x3; // 风险概率
}
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
@Table(name = "nodule_samples")
public class SampleTable {

    @Id
    private String subSampleId;
    private String sampleId;
    private String individualId;

    // 基本信息
    private String sex;
    private Integer birthYear;
    private Integer age;
    private Double bmi;
    private String race;
    private String ethnicity;

    // 生活习惯
    private String smokingStatus;
    private String secondHandSmoke;
    private Integer smokingYears;
    private Integer dailySmokingAmount;
    private Integer quitSmokingYears;
    private String alcoholFreq;
    private String occExposure;

    // 家族与既往史
    private String famHistoryCancer;
    private String relativeCancerHistory;
    private String pastMedicalHistory;
    private String primarySymptoms;

    // 实验室指标
    private Double cyfra21_1;
    private Double cea;
    private Double nse;
    private Double crp;
    private Double pct;
    private Integer esr;
    private String tbTest;

    // 结节影像学特征
    private String malignancyPotential;
    private Integer noduleCount;
    private String noduleCountCat;
    private Double majorDiameter;
    private Double minorDiameter;
    private String noduleSizeCat;
    private String noduleDensityCat;
    private String densityUniformity;
    private Double solidRatio;
    private String morphology;
    private String location;
    private String spiculation;
    private String clarity;
    private Integer vdtDays;

    // 设备参数
    private String deviceModel;
    private String sliceThickness;
    private String windowInfo;
    private String isEnhanced;
    private String checkType;
    private String acquisition;

    // 时间组织
    private String collectionTime;
    private String diagnosisTime;
    private String tissue;
    private String tissueType;

    // 病理基因
    private Double invasionDepth;
    private String lymphMeta;
    private String vascularInvasion;
    private String ihcResults;
    private String geneResults;

    // 治疗随访
    private String surgery;
    private String chemo;
    private String radio;
    private String targetImmuno;
    private String efficacy;
    private String followupTime;
    private String noduleChange;
    private String survivalStatus;
    private Integer survivalMonths;
    private String stage;

    // 组学特有
    private String omicsType;
    private String dataFormat;
}
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
    private String birthYear;
    private String age;
    private String bmi;
    private String race;
    private String ethnicity;

    // 生活习惯
    private String smokingStatus;
    private String secondHandSmoke;
    private String smokingYears;
    private String dailySmokingAmount;
    private String quitSmokingYears;
    private String alcoholFreq;
    private String occExposure;

    // 家族与既往史
    private String famHistoryCancer;
    private String relativeCancerHistory;
    private String pastMedicalHistory;
    private String primarySymptoms;

    // 实验室指标
    private String cyfra21_1;
    private String cea;
    private String nse;
    private String crp;
    private String pct;
    private String esr;
    private String tbTest;

    // 结节影像学特征
    private String malignancyPotential;
    private String noduleCount;
    private String noduleCountCat;
    private String majorDiameter;
    private String minorDiameter;
    private String noduleSizeCat;
    private String noduleDensityCat;
    private String densityUniformity;
    private String solidRatio;
    private String morphology;
    private String location;
    private String spiculation;
    private String clarity;
    private String vdtDays;

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
    private String invasionDepth;
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
    private String survivalMonths;
    private String stage;

    // 组学特有
    private String omicsType;
    private String dataFormat;
    
    // 新增字段
    private String source;
    private String cancerType;
    private String prjectid;
    private String others;
}
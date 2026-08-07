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
@Table(name = "lungnod_data")
public class LungData {
    @Id
    private String myDatasetId;
    private String myProjectId;
    private String datasetId;
    private String bioProjectId;
    private String projectId;
    private String datasetName;
    private String species;
    private String strategy;
    private Integer sampleNumber;
    private String disease;
    private String tissue;
    private String omicsType;
    private String dataFormat;
}

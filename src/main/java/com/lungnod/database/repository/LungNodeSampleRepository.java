package com.lungnod.database.repository;

import com.lungnod.database.model.LungNodeSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LungNodeSampleRepository extends JpaRepository<LungNodeSample, String> {
    List<LungNodeSample> findByStage(String stage);
}

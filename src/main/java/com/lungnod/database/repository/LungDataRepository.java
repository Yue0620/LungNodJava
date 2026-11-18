package com.lungnod.database.repository;

import com.lungnod.database.model.LungData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LungDataRepository extends JpaRepository<LungData, String> {
    List<LungData> findByMyProjectId(String myProjectId);
    List<LungData> findByOmicsType(String omicsType);
    List<LungData> findByDisease(String disease);
    List<LungData> findByTissue(String tissue);
}

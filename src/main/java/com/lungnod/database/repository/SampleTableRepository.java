package com.lungnod.database.repository;

import com.lungnod.database.model.SampleTable; // 导入新类
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SampleTableRepository extends JpaRepository<SampleTable, String> {
    // 自动根据 stage 字段查询，注意变量名要和 SampleTable 里的 stage 对应
    List<SampleTable> findByStage(String stage);
}
package com.imdad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.imdad.entity.PlanEntity;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Integer>{

    @Query("update PlanEntity set activeSwitch = :status where planId = :planId")
    public Integer updatePlanStatus(Integer planId, String status);
}

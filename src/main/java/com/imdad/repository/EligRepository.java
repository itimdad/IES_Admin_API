package com.imdad.repository;

import com.imdad.entity.EligEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligRepository extends JpaRepository<EligEntity, Integer> {
}

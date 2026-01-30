package com.imdad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class EligEntity {

    @Id
    private Integer edTraceId;

    private String caseNum;
    private String planName;
    private String planStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double benefitAmount;
    private String denialReason;

    @CreatedDate
    private LocalDate createdDate;


}

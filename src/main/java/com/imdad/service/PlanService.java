package com.imdad.service;

import java.util.List;

import com.imdad.binding.PlanForm;
import com.imdad.entity.PlanEntity;

public interface PlanService {

	public boolean addPlan(PlanForm form);
	
	public boolean updatePlan(PlanForm form);
	
	public List<PlanEntity> getPlans();
}

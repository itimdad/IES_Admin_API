package com.imdad.service;

import java.util.List;

import com.imdad.binding.PlanForm;
import com.imdad.binding.UserAccountForm;
import com.imdad.entity.PlanEntity;


public interface PlanService {

	public boolean addPlan(PlanForm form);
	
	public List<PlanForm> fetchPlans();
	
	public PlanForm fetchPlanById(Integer planId);

	public String changePlanStatus(Integer planId, String status);
}

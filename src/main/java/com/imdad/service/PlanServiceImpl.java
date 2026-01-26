package com.imdad.service;

import java.util.List;

import com.imdad.binding.PlanForm;
import com.imdad.binding.UserAccountForm;
import com.imdad.entity.PlanEntity;

public class PlanServiceImpl implements PlanService{


	@Override
	public boolean addPlan(PlanForm form) {
		return false;
	}

	@Override
	public List<UserAccountForm> fetchUserAccounts(PlanForm form) {
		return List.of();
	}

	@Override
	public PlanForm getUserPlans() {
		return null;
	}

	@Override
	public String changePlanStatus(Integer planId, String status) {
		return "";
	}
}

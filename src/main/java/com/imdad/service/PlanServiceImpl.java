package com.imdad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.imdad.binding.PlanForm;
import com.imdad.binding.UserAccountForm;
import com.imdad.entity.PlanEntity;
import com.imdad.repository.PlanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService{

	PlanRepository planRepository;

	PlanServiceImpl(PlanRepository planRepository){
		this.planRepository = planRepository;
	}

	@Override
	public boolean addPlan(PlanForm form) {

		PlanEntity planEntity = new PlanEntity();
		BeanUtils.copyProperties(form, planEntity);

		planEntity.setActiveSwitch("Y");
		planRepository.save(planEntity);

		return true;
	}

	@Override
	public List<PlanForm> fetchPlans() {

		List<PlanEntity> all = planRepository.findAll();

		ArrayList<PlanForm> planForm = new ArrayList<>();

		for(PlanEntity planEntity : all) {
			PlanForm plan = new PlanForm();
			BeanUtils.copyProperties(planEntity, plan);
			planForm.add(plan);
		}
		return planForm;
	}

	@Override
	public PlanForm fetchPlanById(Integer planId) {

		Optional<PlanEntity> byId = planRepository.findById(planId);

		if(byId.isPresent()) {
			PlanEntity planEntity = byId.get();

			PlanForm planForm = new PlanForm();
			BeanUtils.copyProperties(planEntity,  planForm);

			return planForm;
		}
		return null;
	}


	@Override
	public String changePlanStatus(Integer planId, String status) {

		Integer cnt = planRepository.updatePlanStatus(planId, status);

		if(cnt > 0){
			return "success";
		}
		return "Something went wrong";
	}
}

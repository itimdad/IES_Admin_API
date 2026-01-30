package com.imdad.service;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import com.imdad.entity.EligEntity;
import com.imdad.entity.UserEntity;
import com.imdad.repository.EligRepository;
import com.imdad.repository.PlanRepository;
import com.imdad.repository.UserRepository;
import com.imdad.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	EmailUtils emailUtils;
	PlanRepository planRepository;

	EligRepository eligRepository;

	UserServiceImpl(UserRepository userRepository,
					EmailUtils emailUtils,
					PlanRepository planRepository,
					EligRepository eligRepository){
		this.userRepository = userRepository;
		this.emailUtils = emailUtils;
		this.planRepository = planRepository;
		this.eligRepository = eligRepository;
	}

	@Override
	public String login(LoginForm form) {
		//TODO  pending
		UserEntity user = userRepository.findByEmailAndPwd(form.getEmail(), form.getPwzzd());

		if(user == null) {
			return "Invalid Credentials";
		}
		//Account switch can be active and unlocked then we will allow user to login
		if("Y".equals(user.getActiveSwitch()) && "UNLOCKED".equals(user.getAccountStatus())) {
			return "Success";
		}
		else {
			return "Account Locked/ In-Active";
		}
	}

	@Override
	public boolean recoverPwd(String email) {

		UserEntity user = userRepository.findByEmail(email);

		if(user == null) {
			return false;
		}
		String body = "";
		String subject = "";
		String to = email;

		try {
			emailUtils.sendMail(subject, to, body);
		}
		catch(Exception e) {
			return false;
		}

		return true;
	}


	@Override
	public DashboardCards getDashboardData() {
		// TODO Auto-generated method stub
		long count = planRepository.count();

		List<EligEntity> all = eligRepository.findAll();

		long approvedCnt =
				all.stream().filter(ed -> ed.getPlanStatus().equals("AP")).count();

		long denialCnt =
				all.stream().filter(ed -> ed.getPlanStatus().equals("DN")).count();

		Double sum =
				all.stream().mapToDouble(EligEntity::getBenefitAmount).sum();

		DashboardCards dashboardCards = new DashboardCards();
		dashboardCards.setTotalPlans(count);
		dashboardCards.setTotalBenifitGiven(sum);
		dashboardCards.setDeniedCitizens(denialCnt);
		dashboardCards.setApprovedCitizens(approvedCnt);

		return dashboardCards;
	}

}

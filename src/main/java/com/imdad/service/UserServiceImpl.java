package com.imdad.service;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import com.imdad.entity.UserEntity;
import com.imdad.repository.PlanRepository;
import com.imdad.repository.UserRepository;
import com.imdad.utils.EmailUtils;

public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	EmailUtils emailUtils;
	PlanRepository planRepository;

	UserServiceImpl(UserRepository userRepository,
					EmailUtils emailUtils,
					PlanRepository planRepository){
		this.userRepository = userRepository;
		this.emailUtils = emailUtils;
		this.planRepository = planRepository;
	}

	@Override
	public String login(LoginForm form) {

		//TODO  pending
//		UserEntity user = userRepository.findByEmailAndPwd();
//
//		if(user == null) {
//
//		}s
		return "";
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

		DashboardCards dashboardCards = new DashboardCards();
		dashboardCards.setTotalPlans(count);
		dashboardCards.setTotalBenifitGiven(25l);
		dashboardCards.setDeniedCitizens(33l);
		dashboardCards.setApprovedCitizens(66l);

		return dashboardCards;
	}

}

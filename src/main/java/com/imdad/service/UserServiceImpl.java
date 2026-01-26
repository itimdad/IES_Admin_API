package com.imdad.service;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import com.imdad.entity.UserEntity;
import com.imdad.repository.UserRepository;

public class UserServiceImpl implements UserService {

	UserRepository userRepository;

	UserServiceImpl(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	@Override
	public String login(LoginForm form) {

		//TODO  pending
//		UserEntity user = userRepository.findByEmailAndPwd();
//
//		if(user == null) {
//
//		}
		return "";
	}

	@Override
	public boolean recoverPwd(String email) {
		return false;
	}


	@Override
	public DashboardCards getDashboardData() {
		// TODO Auto-generated method stub
		return null;
	}

}

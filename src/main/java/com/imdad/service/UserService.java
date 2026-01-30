package com.imdad.service;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	public String login(LoginForm form);
	
	public boolean recoverPwd(String email);
	
	public DashboardCards getDashboardData();
}

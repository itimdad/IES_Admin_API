package com.imdad.service;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;

public interface UserService {

	public boolean login(LoginForm form);
	
	public boolean forgotPwd(String email);
	
	public DashboardCards getDashboardData();
}

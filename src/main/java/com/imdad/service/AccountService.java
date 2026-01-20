package com.imdad.service;

import java.util.List;

import com.imdad.binding.UserAccountForm;
import com.imdad.entity.UserEntity;

public interface AccountService {

	public boolean createAccount(UserAccountForm form);
	
	public List<UserEntity> getAllCaseworkers();
	
	public boolean accountActivateDeactivateSW(Integer userId);
}

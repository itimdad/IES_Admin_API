package com.imdad.service;

import java.util.List;

import com.imdad.binding.UnlockAccForm;
import com.imdad.binding.UserAccountForm;
import com.imdad.entity.UserEntity;

public interface AccountService {

	public boolean createAccount(UserAccountForm form) throws Exception;
	
	public List<UserAccountForm> fetchUserAccounts();

	public UserAccountForm getUserAccById(Integer userId);
	
	public String changeAccountStatus(Integer userId, String status);

	public String unlockAccount(UnlockAccForm unlockAccForm);
}

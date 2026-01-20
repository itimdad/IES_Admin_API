package com.imdad.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imdad.binding.UserAccountForm;
import com.imdad.entity.UserEntity;

@Service
public class AccountServiceImpl implements AccountService{

	@Override
	public boolean createAccount(UserAccountForm form) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserEntity> getAllCaseworkers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean accountActivateDeactivateSW(Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

}

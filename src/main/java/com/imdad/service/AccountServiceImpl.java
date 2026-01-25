package com.imdad.service;

import java.util.List;

import com.imdad.repository.UserRepository;
import com.imdad.utils.AppConstants;
import com.imdad.utils.EmailUtils;
import com.imdad.utils.PwdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.imdad.binding.UserAccountForm;
import com.imdad.entity.UserEntity;

@Service
public class AccountServiceImpl implements AccountService{

	private final UserRepository userRepository;
	private final PwdUtils pwdUtils;
	private final EmailUtils emailUtils;

	AccountServiceImpl(UserRepository userRepository, PwdUtils pwdUtils, EmailUtils emailUtils) {
		this.userRepository = userRepository;
		this.pwdUtils = pwdUtils;
		this.emailUtils = emailUtils;
	}



	@Override
	public boolean createAccount(UserAccountForm form) {
		// check user exits or not if exist return user already Exist
		UserEntity byEmail = userRepository.findByEmail(form.getEmail());

		if(byEmail != null) {
			return false;
		}
		UserEntity  user = new UserEntity();
		//copying data into userEntity
		BeanUtils.copyProperties(form, user);

		String password = pwdUtils.pwdGenerator();
		user.setPwzd(password);

		StringBuilder body = new StringBuilder();
		body.append(AppConstants.EMAIL_BODY + password);

		boolean isSend = false;
		try {
			emailUtils.sendMail(AppConstants.EMAIL_SUBJECT, user.getEmail(), body.toString());
			isSend = true;
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
        userRepository.save(user);

		return isSend;
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

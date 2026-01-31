package com.imdad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.imdad.binding.UnlockAccForm;
import com.imdad.repository.UserRepository;
import com.imdad.constants.AppConstants;
import com.imdad.utils.EmailUtils;
import com.imdad.utils.PwdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imdad.binding.UserAccountForm;
import com.imdad.entity.UserEntity;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private  PwdUtils pwdUtils;
	@Autowired
	private  EmailUtils emailUtils;

	@Override
	public boolean createAccount(UserAccountForm form) {
		// check user exits or not if exist return user already Exist
//		UserEntity byEmail = userRepository.findByEmail(form.getEmail());
//
//		if(byEmail != null) {
//			return false;
//		}
		//copying data into userEntity
		UserEntity  user = new UserEntity();
		BeanUtils.copyProperties(form, user);

		//generate and set password,
		String password = pwdUtils.pwdGenerator();
		user.setPwd(password);

		//set account status, and save into database
		user.setActiveSwitch("Y");
		user.setAccountStatus("LOCKED");
		userRepository.save(user);

		StringBuilder body = new StringBuilder();
		body.append(AppConstants.EMAIL_BODY + password);

		boolean isSend = false;
		try {
			//send email
			emailUtils.sendMail(AppConstants.EMAIL_SUBJECT, user.getEmail(), body.toString());
			isSend = true;
		} catch (Exception e) {
            throw new RuntimeException(e);
        }

		return isSend;
	}

	@Override
	public List<UserAccountForm> fetchUserAccounts() {
		// retrieving all case worker users
		List<UserEntity> userEntity = userRepository.findAll();

		List<UserAccountForm> users = new ArrayList<>();

		for(UserEntity entity : userEntity) {
			UserAccountForm userAccountForm = new UserAccountForm();
			BeanUtils.copyProperties(entity, userAccountForm);
			users.add(userAccountForm);
		}

		return users;
	}

	@Override
	public UserAccountForm getUserAccById(Integer userId) {

		Optional<UserEntity> byId = userRepository.findById(userId);

		if(byId.isPresent()) {
			UserEntity entity = byId.get();
			UserAccountForm user = new UserAccountForm();
			BeanUtils.copyProperties(entity, user);

			return user;
		}

		return null;
	}

	@Override
	public String changeAccountStatus(Integer userId, String status) {
		// Activate and deactivate account
		int cnt = userRepository.updateAccountStatus(userId, status);

		if(cnt > 0) {
			return "status changed";
		}

		return "Failed to changed";
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {
		//check new password and  confirm password is same or not
		if(!unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmPwd())) {
			return "new and confirm password should be same";
		}

		//get user by email
		UserEntity user = userRepository.findByEmail(unlockAccForm.getEmail());

		//compare password and account status if LOCKED then UNLOCKED it and change password
		if(unlockAccForm.getOldPwd().equals(user.getPwd())){
			user.setAccountStatus("UNLOCKED");
			user.setPwd(unlockAccForm.getNewPwd());

			userRepository.save(user);

			return "Account unlocked";
		}

		return "Incorrect password recheck it";
	}

}

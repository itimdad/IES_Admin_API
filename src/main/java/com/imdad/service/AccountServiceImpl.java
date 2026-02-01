package com.imdad.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.imdad.binding.UnlockAccForm;
import com.imdad.repository.UserRepository;
import com.imdad.constants.AppConstants;
import com.imdad.utils.EmailUtils;
import com.imdad.utils.PwdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public boolean createAccount(UserAccountForm form)  {
		// TODO check user exits or not if exist return user already Exist

        logger.debug("Account creating process started");
		//copying data into userEntity
		UserEntity  user = new UserEntity();
		BeanUtils.copyProperties(form, user);

		//generate and set password,
		String password = pwdUtils.pwdGenerator();
		user.setPwd(password);
		user.setRoleId(1);
		//set account status, and save into database
		user.setActiveSwitch("Y");
		user.setAccountStatus("LOCKED");

		userRepository.save(user);
        logger.info("Account created successfully");

		String subject = AppConstants.EMAIL_SUBJECT;
		String body = readEmailBody("REG_EMAIL_BODY.txt", user);
		String email = user.getEmail();
		boolean isSend = false;
		try {
			//send email
			emailUtils.sendMail(subject, email, body);
			isSend = true;
		}
		catch (Exception e) {
            logger.error("Failed to send email{}", e.getMessage(), e);

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

		logger.debug("Fetching user account by id ");
		Optional<UserEntity> byId = userRepository.findById(userId);

		if(byId.isPresent()) {
			UserEntity entity = byId.get();
			UserAccountForm user = new UserAccountForm();
			BeanUtils.copyProperties(entity, user);

			logger.info("Fetching user account by id successfully");
			return user;
		}
		logger.info("Fetching user account by id failed");
		return null;
	}

	@Override
	public String changeAccountStatus(Integer userId, String status) {
		// Activate and deactivate account
		logger.debug("Changing status of user account by id ");
		int cnt = userRepository.updateAccountStatus(userId, status);

		if(cnt > 0) {
			logger.info("Changing status of user account by id successfully");
			return "success";
		}
		logger.error("Changing status of user account by id failed");
		return "Failed to changed";
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {
		logger.debug("Unlocking process user account by id ");
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
			logger.info("Account unlocked successfully");
			return "Account unlocked";
		}
		logger.info("failed to unlock account");
		return "Incorrect password recheck it";
	}

	private String readEmailBody(String fileName, UserEntity user) {
		StringBuilder body = new StringBuilder();

		try (Stream<String> lines = Files.lines(Paths.get(fileName))){

			lines.forEach(line -> {
				line = line.replace("${FNAME}", user.getFullName());
				line = line.replace("${EMAIL}", user.getEmail());
				line = line.replace("${TEMP_PWD}", user.getPwd());
				body.append(line);
			});

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return body.toString();
	}

}

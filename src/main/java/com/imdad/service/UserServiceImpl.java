package com.imdad.service;

import com.imdad.binding.DashboardCards;
import com.imdad.binding.LoginForm;
import com.imdad.binding.UserAccountForm;
import com.imdad.entity.EligEntity;
import com.imdad.entity.UserEntity;
import com.imdad.repository.EligRepository;
import com.imdad.repository.PlanRepository;
import com.imdad.repository.UserRepository;
import com.imdad.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailUtils emailUtils;

	@Autowired
	PlanRepository planRepository;

	@Autowired
	EligRepository eligRepository;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public String login(LoginForm form) {
		//TODO  pending
		UserEntity user = userRepository.findByEmailAndPwd(form.getEmail(), form.getPwzzd());

		logger.debug("Login process started");

		if(user == null) {
			logger.error("Login failed because invalid credentials");
			return "Invalid Credentials";
		}
		//Account switch can be active and unlocked then we will allow user to login
		if("Y".equals(user.getActiveSwitch()) && "UNLOCKED".equals(user.getAccountStatus())) {
			logger.info("User logged in successfully");
			return "Success";
		}
		else {
			logger.error("Login failed may be Account locked or inactive account");
			return "Account Locked/ In-Active";
		}
	}

	@Override
	public boolean recoverPwd(String email) {

		UserEntity user = userRepository.findByEmail(email);
		logger.debug("Recover password process started");

		if(user == null) {
			logger.error("Recover password failed because invalid credentials");
			return false;
		}
		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user);
		String subject = "";
		String to = email;

		try {
			emailUtils.sendMail(subject, to, body);
			logger.info("Password recovered and sent to the email");
		}
		catch(Exception e) {
			logger.error("Failed to recover password get this issue" + e.getMessage());
			return false;
		}

		logger.debug("Recover password process finished");
		return true;
	}


	@Override
	public DashboardCards getDashboardData() {
		// TODO Auto-generated method stub
		logger.debug("getDashboardData process started");
		long count = planRepository.count();

		List<EligEntity> all = eligRepository.findAll();

		long approvedCnt =
				all.stream().filter(ed -> ed.getPlanStatus().equals("AP")).count();

		long denialCnt =
				all.stream().filter(ed -> ed.getPlanStatus().equals("DN")).count();

		Double sum =
				all.stream().mapToDouble(EligEntity::getBenefitAmount).sum();

		DashboardCards dashboardCards = new DashboardCards();
		dashboardCards.setTotalPlans(count);
		dashboardCards.setTotalBenifitGiven(sum);
		dashboardCards.setDeniedCitizens(denialCnt);
		dashboardCards.setApprovedCitizens(approvedCnt);

		logger.debug("getDashboardData process finished");
		return dashboardCards;
	}

	@Override
	public UserAccountForm getUserByEmail(String email) {
		UserEntity byEmail = userRepository.findByEmail(email);

		logger.debug("fetching user account process started ");
		UserAccountForm user = new UserAccountForm();

		BeanUtils.copyProperties(byEmail, user);
		logger.info("User account fetched successfully");
		logger.debug("fetching user account process finished");
		return user;
	}

	private String readEmailBody(String fileName, UserEntity user) {

		StringBuilder sb = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get(fileName))){

			lines.forEach( line -> {
				line = line.replace("${FNAME}", user.getFullName());
				line = line.replace("${EMAIL}", user.getEmail());
				line = line.replace("{pwd}", user.getPwd());

					}
			);

		} catch (Exception e) {

		}

		return sb.toString();
	}

}

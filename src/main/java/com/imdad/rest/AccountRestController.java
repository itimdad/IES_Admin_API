package com.imdad.rest;

import com.imdad.binding.UnlockAccForm;
import com.imdad.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imdad.binding.UserAccountForm;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountRestController {

	@Autowired
	AccountService accountService;

	Logger logger = LoggerFactory.getLogger(AccountRestController.class);

	@PostMapping(
			value = "/user",
			consumes = {"application/xml" , "application/json"}
			)
	public ResponseEntity<String> createUserAccount(
			@RequestBody UserAccountForm accountForm) {

		logger.debug("Create user account process");
		boolean isCreated = accountService.createAccount(accountForm);
		if (isCreated) {
			logger.info("Account created successfully");
			return new ResponseEntity<String>("created successfully", HttpStatus.CREATED);
		}
		logger.info("Account creation failed");
		return new ResponseEntity<String>("Email Already Exist", HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@GetMapping(
			value = "/users",
			produces = {"application/json", "application/xml"}
	)
	public ResponseEntity<List<UserAccountForm>> getAllUsers(){

		logger.debug("fetch all users process started");
		List<UserAccountForm> userAccountForms =
				accountService.fetchUserAccounts();
		logger.info("all users fetched....");

		logger.debug("fetch all users process finished");
		return new ResponseEntity<List<UserAccountForm>>(userAccountForms, HttpStatus.OK);

	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserAccountForm> getUser(@PathVariable("userId") Integer userId) {
		UserAccountForm userAccById = accountService.getUserAccById(userId);

		return new ResponseEntity<>(userAccById, HttpStatus.OK);
	}

	@PutMapping(
			value = "/user/{userId}/{status}",
			consumes = {"application/json", "application/xml"}
	)
	public ResponseEntity<List<UserAccountForm>> accountStatusSwitch(@PathVariable Integer userId,
												 @PathVariable String status) {
		accountService.changeAccountStatus(userId, status);

		List<UserAccountForm> userAccountForms = accountService.fetchUserAccounts();

		return new ResponseEntity<>(userAccountForms, HttpStatus.OK);
	}

	@PostMapping(
			value = "/unlock",
			consumes = {"application/json", "application/xml"}
	)
	public ResponseEntity<String> unlockUserAccount(
			@RequestBody UnlockAccForm unlockAccForm) {

		String status = accountService.unlockAccount(unlockAccForm);

		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}

package com.imdad.rest;

import com.imdad.binding.UnlockAccForm;
import com.imdad.entity.UserEntity;
import com.imdad.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imdad.binding.UserAccountForm;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountRestController {

	AccountService accountService;

	AccountRestController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping(
			value = "/user",
			consumes = {"application/xml" , "application/json"}
			)
	public ResponseEntity<String> createUserAccount(
			@RequestBody UserAccountForm accountForm) {

		boolean isCreated = accountService.createAccount(accountForm);
		if (isCreated) {
			return new ResponseEntity<String>("created successfully", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Email Already Exist", HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@GetMapping(
			value = "/users",
			produces = {"application/json", "application/xml"}
	)
	public ResponseEntity<List<UserAccountForm>> getAllUsers(){

		List<UserAccountForm> userAccountForms =
				accountService.fetchUserAccounts();

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

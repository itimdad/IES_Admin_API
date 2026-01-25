package com.imdad.rest;

import com.imdad.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imdad.binding.UserAccountForm;

@RestController
public class AccountRestController {

	AccountService accountService;

	AccountRestController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping(
			value = "/create",
			consumes = {"application/xml" , "application/json"}
			)
	public ResponseEntity<String> createUserAccount(
			@RequestBody UserAccountForm accountForm) {

		boolean isCreated = accountService.createAccount(accountForm);
		if(isCreated) {
			return new ResponseEntity<String>("created successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Email Already Exist", HttpStatus.IM_USED);
		
	}
}

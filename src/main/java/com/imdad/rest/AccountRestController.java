package com.imdad.rest;

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
			value = "/cw-create",
			consumes = {"application/xml" , "application/json"}
			)
	public ResponseEntity<String> createUserAccount(
			@RequestBody UserAccountForm accountForm) {

		boolean isCreated = accountService.createAccount(accountForm);
		if (isCreated) {
			return new ResponseEntity<String>("created successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Email Already Exist", HttpStatus.IM_USED);
	}

	@GetMapping("/cw-fetch")
	public ResponseEntity<List<UserEntity>> getAllUsers(){
		List<UserEntity> allCaseworkers =
				accountService.fetchUserAccounts();
		return new ResponseEntity<List<UserEntity>>(allCaseworkers, HttpStatus.OK);
	}

	@GetMapping(
			value = "/cw-switch",
			consumes = {"application/json", "application/xml"}
	)
	public ResponseEntity<?> accountSwitch(@RequestParam Integer userId, @RequestParam String status) {
		boolean isChanged = accountService.changeAccountStatus(userId, status);

		if(isChanged) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>("Something wrong", HttpStatus.OK);
	}
}

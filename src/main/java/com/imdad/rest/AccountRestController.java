package com.imdad.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imdad.binding.UserAccountForm;

@RestController
public class AccountRestController {

	@PostMapping(
			value = "/create",
			consumes = {"application/xml" , "application/json"}
			)
	public ResponseEntity<String> createUserAccount(
			@RequestParam UserAccountForm accountForm) {
		
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
		
	}
}

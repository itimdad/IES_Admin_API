package com.imdad.binding;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAccountForm {
	
	private String fullName;
	private String email;
	private String phno;
	private String gender;
	private LocalDate dob;
	private String userSSN;
}

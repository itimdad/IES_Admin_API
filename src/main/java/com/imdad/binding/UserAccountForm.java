package com.imdad.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserAccountForm {
	
	private String name;
	private String email;
	private Long phno;
	private String gender;
	private LocalDate dob;
	private String userSSN;
}

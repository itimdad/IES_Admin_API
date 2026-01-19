package com.imdad.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IES_USERS")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fullName;
	private String email;
	private String pwzd;
	private String phno;
	private String gender;
	private LocalDate dob;
	@Column(name = "ssn_num")
	private String userSSN;
	private String activeSwitch = "Y";
	private String accountStatus = "LOCKED";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	UserRolesEntity roles;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<PlanEntity> plans;
	
}

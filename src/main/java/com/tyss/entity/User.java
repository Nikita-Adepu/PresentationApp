package com.tyss.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tyss.enums.Role;
import com.tyss.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User_details")
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @NotEmpty(message = "Name cannot be empty")
	private String name;

	@Email
	@NotEmpty(message = "Email cannot be empty")
	@Column(unique = true)
	private String email;

//	@Size(min = 6, max = 10, message = "Phone number must be exactly 10 digits")
	@Min(1000000000L) // Minimum 10-digit number
	@Max(999999999999999L) 
	private Long phone;

	@Size(min = 8, max = 10, message = "password is between 8-10 characters")
	@JsonIgnore
	private String password;

	@OneToMany(mappedBy = "user")
	@JsonIgnore 
	private List<Presentation> presentations;

//	@OneToMany(mappedBy = "user")
//	private List<Rating> ratings;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Enumerated(EnumType.STRING)
	private Role role;

	private Double userTotalScore;

}

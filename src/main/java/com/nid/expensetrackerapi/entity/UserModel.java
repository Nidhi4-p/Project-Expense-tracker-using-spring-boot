package com.nid.expensetrackerapi.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.*;
import lombok.Data;
@Data
public class UserModel {

	@NotBlank(message="Name should not be empty")
	private String name;
	
	@NotNull(message="Name should not be empty")
	@Email(message="Enter valid email")
	private String email;
	
	@NotNull(message="Password should not be null")
	@Size(min=5,message="Password should have atleast 5 characters")
	private String password;
	
	private Long age  ;
	
}

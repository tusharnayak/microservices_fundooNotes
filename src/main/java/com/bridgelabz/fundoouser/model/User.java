package com.bridgelabz.fundoouser.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document("userlogin")
@Data
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@NotBlank
	private String firstName;
	private String lastName;
	@NotBlank
	private String userName;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String confirmPassword;
	private boolean isValid;
	private String forgetPassword;
	private String profilePic;
	private LocalDateTime loginDateTime;
}

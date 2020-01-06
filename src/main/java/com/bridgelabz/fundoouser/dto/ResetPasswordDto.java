package com.bridgelabz.fundoouser.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class ResetPasswordDto {
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;

	
}

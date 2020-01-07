package com.bridgelabz.fundoouser.services;
import com.bridgelabz.fundoouser.dto.ForgetPasswordDto;
import com.bridgelabz.fundoouser.dto.LoginDto;
import com.bridgelabz.fundoouser.dto.RegistrationDto;
import com.bridgelabz.fundoouser.response.Response;

public interface UserService {

	public Response addUser(RegistrationDto registrationdto);

	public Response loginUser(LoginDto logindto, String email);

	public Response forgetPassword(ForgetPasswordDto forgetpassword);
	
	public Response accountVerification(String token);
	
	public Response getAllUser();

}

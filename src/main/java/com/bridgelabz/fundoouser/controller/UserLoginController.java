package com.bridgelabz.fundoouser.controller;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoouser.dto.ForgetPasswordDto;
import com.bridgelabz.fundoouser.dto.LoginDto;
import com.bridgelabz.fundoouser.dto.RegistrationDto;
import com.bridgelabz.fundoouser.response.Response;
import com.bridgelabz.fundoouser.services.UserService;

@RestController
@RequestMapping("/user")
public class UserLoginController {

	@Autowired
	private UserService serviceImpl;

	@PostMapping("/adduser")
	public ResponseEntity<Response> addUser(@Valid @RequestBody RegistrationDto registrationDto) {
//		String result = serviceimpl.addUser(registrationdto);
//		System.out.println("result:::" + result);
		return new ResponseEntity<Response>(serviceImpl.addUser(registrationDto), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginDto loginDto, @RequestHeader String email) {
//		String result = serviceimpl.loginUser(logindto,token);
//		System.out.println("login successful"+result);
		return new ResponseEntity<Response>(serviceImpl.loginUser(loginDto, email), HttpStatus.OK);
	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetpassword) {
		return new ResponseEntity<Response>(serviceImpl.forgetPassword(forgetpassword), HttpStatus.OK);
	}
	@PostMapping("/check_valid_user_or_not")
	public ResponseEntity<Response>checkValidUser(@RequestBody @RequestParam String token){
		return new ResponseEntity<Response>(serviceImpl.accountVerification(token),HttpStatus.OK);
	}

}

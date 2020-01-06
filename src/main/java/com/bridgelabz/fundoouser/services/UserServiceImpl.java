package com.bridgelabz.fundoouser.services;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoouser.dto.ForgetPasswordDto;
import com.bridgelabz.fundoouser.dto.LoginDto;
import com.bridgelabz.fundoouser.dto.RegistrationDto;
import com.bridgelabz.fundoouser.model.User;
import com.bridgelabz.fundoouser.repository.UserRepository;
import com.bridgelabz.fundoouser.response.Response;
import com.bridgelabz.fundoouser.utility.Jms;
import com.bridgelabz.fundoouser.utility.Jwt;

@Service
@PropertySource("classpath:message.properties")
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRepository repository;
	
//	@Autowired
//	private RestTemplate template;

	@Autowired
	private Jwt jwt;

	@Autowired
	private Jms jms;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	private Environment environment;

	/**
	 * @purpose: to add user to the registration page
	 */
	@Override
	@Cacheable(key = "#registrationdto", value = "register")
	public Response addUser(RegistrationDto registrationdto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(registrationdto, User.class);
		String token = jwt.createToken("tusharnayak1996@gmail.com");
//		if (token.isEmpty()) {
//			throw new TokenException("invalid token");
//		}

		jms.sendMail("tusharnayak1996@gmail.com", token);
		String regdPass = registrationdto.getPassword();
		String confirmPass = registrationdto.getConfirmPassword();
		if (regdPass.equals(confirmPass)) {
			user.setPassword(bcryptPasswordEncoder.encode(regdPass));
			repository.save(user);
			return new Response(200, environment.getProperty("USER_ADDED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("PASSWORD_NOT_MATCHED"), HttpStatus.BAD_REQUEST);

	}

	/**
	 * @purpose: for login to the user.
	 */
	@Override
	@Cacheable(key = "#email", value = "loginUser")
	public Response loginUser(LoginDto logindto, String email) {
		User user = repository.findByemail(email);
		if (user != null) {
			boolean isValid = logindto.getPassword().equals(user.getConfirmPassword());
			if (user.getEmail().equals(logindto.getEmail())) {
				if (isValid) {
					bcryptPasswordEncoder.encode(logindto.getPassword());
					repository.save(user);
					return new Response(200, environment.getProperty("LOGIN"), HttpStatus.OK);
				}
				return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
			}
			return new Response(400, environment.getProperty("WRONG_PASSWORD"), HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: if the password is forget, then send a confirmation to the mail
	 * @return: the token is send to the mail id or not
	 */
	@Override
	@Cacheable(key = "#forgetPassordDto", value = "forgetpassword")
	public Response forgetPassword(ForgetPasswordDto forgetPassordDto) {
		User email = repository.findByemail(forgetPassordDto.getEmail());
		System.out.println(email);
		String token = jwt.createToken(forgetPassordDto.getEmail());
		System.out.println(token);
		if (email != null) {
			System.out.println("in email");
			jms.sendMail(forgetPassordDto.getEmail(), token);
			return new Response(200, environment.getProperty("SENDING_TOKEN"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);

	}

	@Override
	@Cacheable(key="#token", value="accountverification")
	public Response accountVerification(String token) {
		String email = jwt.getUserToken(token);
		User user = repository.findByemail(email);
		if (user != null) {
			user.isValid();
			return new Response(200, environment.getProperty("VALID_USER"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_USER"), HttpStatus.BAD_REQUEST);
	}
	
}
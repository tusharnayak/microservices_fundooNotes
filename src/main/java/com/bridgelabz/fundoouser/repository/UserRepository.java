package com.bridgelabz.fundoouser.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoouser.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, String>
{
	public User findByemail(String email);	
}

package com.demo.utility.crud;

import java.util.Iterator;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Person;
import com.demo.model.UserInfo;
import com.demo.utility.crud.repo.PersonRepository;
import com.demo.utility.crud.repo.UserInfoRepository;

@Service
public class DTOService implements IUserService, IPersonService{

	private static Logger logger = LogManager.getLogger(DTOService.class);

	@Autowired
	UserInfoRepository userRepo;

	@Autowired
	PersonRepository personRepo;

	@Override
	public UserInfo findByName(String username) {
		List<UserInfo> userinfo = (List<UserInfo>) userRepo.findAll();
		logger.info("----> UserName::");
		for (UserInfo uInfo : userinfo) {
			return uInfo;
		}
		return null;
	}

	@Override
	public List<UserInfo> findAllUsers() {
		List<UserInfo> allUsers = (List<UserInfo>) userRepo.findAll();
		return allUsers;
	}
	
	@Override
	public UserInfo createUserInfo(UserInfo uInfo) {
		UserInfo savedUInfo = null;
		if (uInfo != null) {
			savedUInfo = userRepo.save(uInfo);
		}
		return savedUInfo;
	}

	@Override
	public List<Person> findAllPersons() {
		List<Person> findPersons = (List<Person>) personRepo.findAll();
		return findPersons;
	}

	@Override
	public List<Person> findByFstName() {
		List<Person> findPrsnsByName = (List<Person>) personRepo.findAll();
		return findPrsnsByName;
	}

	@Override
	public Person createPerson(Person person) {
		Person savedPerson = null;
		if (person != null) {
			savedPerson = personRepo.save(person);
		}
		return savedPerson;
	}
}
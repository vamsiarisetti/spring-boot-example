package com.demo.utility.crud;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.demo.model.UserInfo;

public interface IUserService {

/*	@Query("Select * from UserInfo where username=:username")
	List<UserInfo> findByName(String username);*/

	@Query("Select * from userinfo")
	List<UserInfo> findAllUsers();

	UserInfo createUserInfo(UserInfo uInfo);

	UserInfo findByName(String username);
}
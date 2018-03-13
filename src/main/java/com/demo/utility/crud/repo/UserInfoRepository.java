package com.demo.utility.crud.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long>{
	UserInfo findByUsername(String username);
}
package com.demo.controller;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.UserInfo;
import com.demo.utility.LoginUtils;
import com.demo.utility.crud.repo.UserInfoRepository;

@RestController
public class RestSrvController {

	@Autowired
	private UserInfoRepository urepo;
	
	private static Logger LOG = LogManager.getLogger(RestSrvController.class);

	/**
	 * Returns true when userInfo is not null and false when null
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/UserExists/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean isUserExists(@PathVariable("username") String username) {

		UserInfo uInfo = urepo.findByUsername(username);
		if (uInfo != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@RequestMapping(value="/chkPwdisPrev/{username}/{password}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean chkPrevPassword(@PathVariable("username") String username, @PathVariable("password") String password) {
		try {
			String pwdhashFromDB = "";
			// Creating Hash for the given txt
			String passHash = LoginUtils.demoMD5(password);
			
			// retrieving pwdhash from userInfo table for given user
			UserInfo uInfo = urepo.findByUsername(username);
			if (uInfo != null)
			pwdhashFromDB = uInfo.getPwdhash();
			
			// given and retrieved password are same
			if (pwdhashFromDB.equals(passHash)) {
				return Boolean.TRUE;
			}
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Caught in chkPrevPassword :: "+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
}
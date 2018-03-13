package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.model.Person;
import com.demo.model.UserInfo;
import com.demo.utility.LoginUtils;
import com.demo.utility.crud.DTOService;

@Controller
public class LoginController {

	private static Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	private DTOService dtoSrv;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/login")
	public String sayHello(@RequestParam("name") String name, Model model, HttpSession appsesssion) {
		logger.info(">>>"+name);
		
		UserInfo findByNameUserInfo = dtoSrv.findByName(name);
		if (findByNameUserInfo != null) {
			logger.info("User ::"+findByNameUserInfo.getUsername());
			model.addAttribute("name", findByNameUserInfo.getUsername());
			appsesssion.setAttribute("UserName", findByNameUserInfo.getUsername());
			return "home";
		} else {
			model.addAttribute("errorMsg", name+" Do not Exist in Persistence");
			return "index";
		}
	}

	/*
	 * Redirecting Forgot Password Link control to Register password
	 */
	@RequestMapping("newUser")
	public String doRegisterUser() {
		logger.info("In doRegisterUser");
		return "registerUser";
	}

	/*
	 * Registering New User and Saving to Persistence
	 */
	@PostMapping("RegisterNewUser")
	public String doGetAllUseres(Model model,
    		@RequestParam("FstNm") String fstName, @RequestParam("LstNm") String lstName,
    		@RequestParam("Passwd") String Passwd, @RequestParam("RePasswd") String RePasswd,
    		@RequestParam("email") String email, @RequestParam("UserNm") String UserNm,
    		@RequestParam("Cty") String Cty, HttpSession appsession) {

		boolean blnIsRegVaidated = Boolean.FALSE;
		String errMsg = null;
		try {
			// Validating all mandatory fields for null or empty
			String lgnValDtls = new LoginUtils().doValidateRegDetails(fstName, lstName, Passwd, RePasswd, email, UserNm, Cty);
			if (lgnValDtls != null) {
				String[] loginDtls = lgnValDtls.split("~");
    			for (int i = 0; i < loginDtls.length; i++) {
    				if(loginDtls[0] != null)
    					blnIsRegVaidated = Boolean.valueOf(loginDtls[0]);
					errMsg = loginDtls[1];
				}
			}
			if (blnIsRegVaidated) {
        		Person prsn = new Person();
        		prsn.setFirstName(fstName);
        		prsn.setLastName(lstName);
        		prsn.setEmail(email);

        		String salt = LoginUtils.getRandomSalt();
        		String dbPwdHash = LoginUtils.demoMD5(Passwd + salt);
        		String passHash = LoginUtils.demoMD5(Passwd);

        		UserInfo uInfo = new UserInfo(UserNm, Cty.toUpperCase(), dbPwdHash, salt, passHash);
        		Person person = new Person();
        		person.setFirstName(fstName);
        		person.setLastName(lstName);
        		person.setEmail(email);
        		person.setUserInfo(uInfo);

        		dtoSrv.createUserInfo(uInfo);
        		dtoSrv.createPerson(person);

        		if (uInfo != null) {
        			appsession.setAttribute("UserName", uInfo.getUsername());
				}
        		return "home";
			} else {
				model.addAttribute("RegErrors", errMsg);
				return "registerUser";
			}
		} catch (Exception e) {
			logger.error("Caught in Exception while Registering New User::"+e.getMessage());
			model.addAttribute("RegErrors", e.getMessage());
		}
		return "registerUser";
	}

	@RequestMapping("forgetPwd")
	public String forgotPwd() {
		return "ResetPwd";
	}
	
	@PostMapping("ResetPassword")
	public String resetPassword() {
		return "ResetPwd";
	}

    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String goToHome(ModelMap model) {
    	return "home";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String doLogOut(ModelMap model, HttpServletRequest request, HttpSession appSession) {
    	if (appSession != null) {
			appSession.invalidate();
		}
    	return "logout";
    }

	/*@RequestMapping(value="/RegisterNewUser", method=RequestMethod.POST)
    public String doRegNewUser(ModelMap model,
    		@RequestParam("FstNm") String fstName, @RequestParam("LstNm") String lstName,
    		@RequestParam("Passwd") String Passwd, @RequestParam("RePasswd") String RePasswd,
    		@RequestParam("email") String email, @RequestParam("UserNm") String UserNm,
    		@RequestParam("Cty") String Cty) {
		SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		boolean blnIsRegVaidated = Boolean.FALSE;
		String errMsg = null;
    	try{

    		String lgnValDtls = new LoginUtils().doValidateRegDetails(fstName, lstName, Passwd, RePasswd, email, UserNm, Cty);
    		if (lgnValDtls != null) {
    			String[] loginDtls = lgnValDtls.split("~");
    			for (int i = 0; i < loginDtls.length; i++) {
    				if(loginDtls[0] != null)
    					blnIsRegVaidated = Boolean.valueOf(loginDtls[0]);
					errMsg = loginDtls[1];
				}
			}
    		if (blnIsRegVaidated) {
        		Person prsn = new Person();
        		prsn.setFirstName(fstName);
        		prsn.setLastName(lstName);
        		prsn.setEmail(email);

        		String salt = LoginUtils.getRandomSalt();
        		String dbPwdHash = LoginUtils.demoMD5(Passwd + salt);
        		String passHash = LoginUtils.demoMD5(Passwd);

        		prsn.setUserInfo(new UserInfo(UserNm, Cty.toUpperCase(), dbPwdHash, salt, passHash));
        		long personId = (Long) session.save(prsn);
        		session.getTransaction().commit();
        		return "login";
			} else {
				model.addAttribute("RegErrors", errMsg);
				return "registerUser";
			}
    	} catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
    	}
    	return null;
    }*/
}
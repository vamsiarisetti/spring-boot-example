package com.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.bot.BotUtility;
import com.demo.model.Person;
import com.demo.model.UserInfo;
import com.demo.utility.LoginUtils;
import com.demo.utility.crud.repo.PersonRepository;
import com.demo.utility.crud.repo.UserInfoRepository;

@Controller
public class LoginController {

	private static Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	private UserInfoRepository urepo;

	@Autowired
	private PersonRepository prepo;

	// @Autowired
	// private DTOService dtoSrv;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/login")
	public String sayHello(@RequestParam("name") String name, @RequestParam("pwd") String pwd, Model model,
			HttpSession appsesssion) throws NoSuchAlgorithmException {

		String dbPassword = "";
		String dbSalt = "";
		String userPwdHash = "";
		boolean blnIsloginSuccess = Boolean.FALSE;

		UserInfo uInfoByName = urepo.findByUsername(name);

		if (uInfoByName != null) {
			logger.info("While login :: UserName ::" + uInfoByName.getUsername());
			model.addAttribute("name", uInfoByName.getUsername());
			appsesssion.setAttribute("UserName", uInfoByName.getUsername());

			dbPassword = uInfoByName.getPassword();
			dbSalt = uInfoByName.getSalt();
			userPwdHash = LoginUtils.demoMD5(pwd + dbSalt);
			if (userPwdHash.equalsIgnoreCase(dbPassword)) {
				blnIsloginSuccess = Boolean.TRUE;
			} else {
				blnIsloginSuccess = Boolean.FALSE;
			}

			if (blnIsloginSuccess) {
				System.out.println("LOGIN SUCCESS");
				return "home";
			} else {
				model.addAttribute("errorMsg", "Please Enter valid Credentials...");
				return "index";
			}
		}
		return "index";
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
	public String doGetAllUseres(Model model, @RequestParam("FstNm") String fstName,
			@RequestParam("LstNm") String lstName, @RequestParam("Passwd") String Passwd,
			@RequestParam("RePasswd") String RePasswd, @RequestParam("email") String email,
			@RequestParam("UserNm") String UserNm, @RequestParam("Cty") String Cty, HttpSession appsession) {

		boolean blnIsRegVaidated = Boolean.FALSE;
		String errMsg = null;
		try {
			// Validating all mandatory fields for null or empty
			String lgnValDtls = new LoginUtils().doValidateRegDetails(fstName, lstName, Passwd, RePasswd, email, UserNm,
					Cty);
			if (lgnValDtls != null) {
				String[] loginDtls = lgnValDtls.split("~");
				for (int i = 0; i < loginDtls.length; i++) {
					if (loginDtls[0] != null)
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

				urepo.save(uInfo);
				prepo.save(person);

				if (uInfo != null) {
					appsession.setAttribute("UserName", uInfo.getUsername());
				}
				return "home";
			} else {
				model.addAttribute("RegErrors", errMsg);
				return "registerUser";
			}
		} catch (Exception e) {
			logger.error("Caught in Exception while Registering New User::" + e.getMessage());
		}
		return "registerUser";
	}

	@RequestMapping("forgetPwd")
	public String forgotPwd() {
		return "ResetPwd";
	}

	@RequestMapping("ResetPassword")
	public String resetPassword(ModelMap model,
			@RequestParam("userNm") String userNm, @RequestParam("userPwd") String userPwd) {

		try {
			String salt = LoginUtils.getRandomSalt();
			String dbPwdHash = LoginUtils.demoMD5(userPwd + salt);
			String passHash = LoginUtils.demoMD5(userPwd);

			UserInfo uInfo = new UserInfo();
			uInfo.setPassword(dbPwdHash);
			uInfo.setPwdhash(passHash);
			uInfo.setSalt(salt);
			uInfo.setUsername(userNm);			

			urepo.save(uInfo);
			//urepo.updateUInfo(uInfo);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Caught while resetting password :::"+e);
			e.printStackTrace();
		}
		return "ResetPwd";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String goToHome(ModelMap model) {
		return "home";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogOut(ModelMap model, HttpServletRequest request, HttpSession appSession) {
		if (appSession != null) {
			appSession.invalidate();
		}
		return "logout";
	}

	@RequestMapping(value= {"test", "zz"}, method = RequestMethod.GET)
	public String justTest(Model model, HttpServletRequest request) {
		String servletPath = request.getServletPath();
		System.out.println("VALUE ::: "+servletPath);
		if ("/test".equals(servletPath)) {
			return "test";
		}
		else if ("/zz".equals(servletPath)) {
			return "ZZ";
		}
		return "/";
	}
	/*@RequestMapping(value="test1", method = RequestMethod.POST)
	public String justTest1(Model model, @RequestParam("botInputTxt") String inputText) {
		logger.info("Input String ::"+inputText);
		

	}*/
}
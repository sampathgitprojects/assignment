package com.assignment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.assignment.model.SignupForm;
import com.assignment.model.User;
import com.assignment.repository.UserRepository;
import com.assignment.service.UserDetailServiceImpl;
import com.assignment.utility.AppConstants;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Controller
public class UserController {

	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/indexPage")
	public String indexPage(Model model) {
		return "index";
	}

	@GetMapping("/addUser")
	public String addUser(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return AppConstants.CRETAE;
	}

	@GetMapping("/getUser")
	public String getUser(Model model) {
		List<User> users = (List<User>) repository.findAll();
		model.addAttribute("users", users);
		return AppConstants.GET_USERS;
	}

	@GetMapping("/updateUser")
	public String updateUser(Model model) {
		model.addAttribute("updateForm", new SignupForm());
		return AppConstants.UPDATE;
	}

	@GetMapping("/deleteUser")
	public String deleteUser(Model model) {
		model.addAttribute("deleteForm", new SignupForm());
		return AppConstants.DELETE;
	}

	@PostMapping("saveuser")
	public String save(
			@Valid @ModelAttribute("signupform") SignupForm signupForm,
			BindingResult bindingResult) {
		logger.info("calling save controller to save new user");

		if (!bindingResult.hasErrors()) { // validation errors

			String pwd = signupForm.getPassword();
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			String hashPwd = bc.encode(pwd);

			User newUser = new User();
			newUser.setPasswordHash(hashPwd);
			newUser.setUsername(signupForm.getUsername());
			newUser.setRole("USER");
			newUser.setEmpId(signupForm.getEmpId());
			if (repository.findByUsername(signupForm.getUsername()) == null) {
				repository.save(newUser);
			} else {
				bindingResult.rejectValue("username", "error.userexists",
						"Username already exists");
				return AppConstants.SIGN_UP;
			}
		} else {
			return AppConstants.SIGN_UP;
		}
		return AppConstants.LOGIN_REDIRECT;
	}

	@PutMapping("userInfo")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateUser(@FormParam("empId") String empId,
			@FormParam("username") String name,
			@FormParam("password") String password,
			@FormParam("role") String role, BindingResult bindingResult) {

		User user = userDetailServiceImpl.getUserForEmpId(empId);
		if (StringUtils.isEmpty(user)) {
			bindingResult.rejectValue("passwordCheck", "error.pwdmatch",
					"Passwords does not match");
			return AppConstants.UPDATE;
		} else {
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			String hashPwd = bc.encode(password);
			repository.updateUserInfo(name, hashPwd, role);
			return AppConstants.INDEX;
		}

	}

	@DeleteMapping("/delete")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Boolean deleteeUser(@FormParam("empId") String id) {
		return userDetailServiceImpl.deleteUser(id);
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	public void setUserDetailServiceImpl(
			UserDetailServiceImpl userDetailServiceImpl) {
		this.userDetailServiceImpl = userDetailServiceImpl;
	}

}

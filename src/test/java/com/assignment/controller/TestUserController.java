package com.assignment.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import com.assignment.model.SignupForm;
import com.assignment.model.User;
import com.assignment.repository.UserRepository;
import com.assignment.service.UserDetailServiceImpl;

/**
 * @author sampath this class is created for writing unit test cases for user
 *         controller.
 */
@RunWith(SpringRunner.class)
public class TestUserController {

	@Mock
	private UserRepository repository;

	@Mock
	UserDetailServiceImpl userDetailServiceImpl;

	@Mock
	BindingResult bindingResult;

	@InjectMocks
	UserController userController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userDetailServiceImpl = new UserDetailServiceImpl(this.repository);
		userController = new UserController();
		userController.setRepository(this.repository);
		userController.setUserDetailServiceImpl(userDetailServiceImpl);
	}

	@Test
	public void saveTest() {

		User user = getUserTestData();
		Mockito.when(repository.findByUsername(user.getUsername())).thenReturn(
				user);
		Mockito.when(repository.save(user)).thenReturn(user);
		userController.save(getSignupFormData(), bindingResult);

	}

	@Test
	public void updateUserTest() {

		User user = getUserTestData();
		Mockito.when(
				repository.updateUserInfo(user.getUsername(),
						user.getPasswordHash(), user.getRole())).thenReturn(1);
		Mockito.when(userDetailServiceImpl.getUserForEmpId(user.getEmpId()))
				.thenReturn(user);
		userController.updateUser(user.getEmpId(), user.getUsername(),
				user.getPasswordHash(), user.getRole(), bindingResult);
	}

	private User getUserTestData() {
		return new User("testuser", "testuser", "USER", "sp16902");
	}

	private SignupForm getSignupFormData() {
		SignupForm signupForm = new SignupForm();
		signupForm.setEmpId("Sp16902");
		signupForm.setPassword("testuser");
		signupForm.setRole("USER");
		signupForm.setUsername("testuser");
		return signupForm;

	}
}

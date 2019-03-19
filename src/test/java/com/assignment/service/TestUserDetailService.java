package com.assignment.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.model.User;
import com.assignment.repository.UserRepository;


/**
 * @author sampath
 * this class is created for writing unit test cases for user details service.
 */
@RunWith(SpringRunner.class)
public class TestUserDetailService {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	UserDetailServiceImpl userDetailServiceImpl;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userDetailServiceImpl = new UserDetailServiceImpl(this.userRepository);
    }

	@Test
	public void loadUserByUsernameTest(){
		
		User user = getUserTestData();
		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
		UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUsername());
		assertNotNull(userDetails.getUsername());
		assertNotNull(userDetails.getAuthorities());
		
	}
	
	@Test
	public void getUserForEmpIdTest(){
		
		User user = getUserTestData();
		Mockito.when(userRepository.findByEmpId(user.getEmpId())).thenReturn(user);
		User user1 = userDetailServiceImpl.getUserForEmpId(user.getEmpId());
		assertNotNull(user1.getUsername());
	}
	
	
	@Test
	public void updateUserTest(){
		User user = getUserTestData();
		userDetailServiceImpl.updateUser(user.getUsername(),user.getPasswordHash(),user.getRole());
	}
	
	@Test
	public void deleteUserTest(){
		User user = getUserTestData();
		Mockito.when(userRepository.findByEmpId(user.getEmpId())).thenReturn(user);
		userDetailServiceImpl.deleteUser(user.getEmpId());
	}
	
	private User getUserTestData(){
	     return new User("testuser", "testuser", "USER","sp16902");
		}


}

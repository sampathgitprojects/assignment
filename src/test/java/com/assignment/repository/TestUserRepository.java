package com.assignment.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.model.User;



/**
 * this class is created for writing unit test cases for user repository.
 * @author sampath
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class TestUserRepository {
	
	private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @Test
    public void addUserTest() {
    	User user = getUserTestData();
    	assertNull(user.getId());
    	userRepository.save(user);
    	assertNotNull(user.getId());
    }
    
	@Test
    public void findByEmpIdTest() {
		User user = getUserTestData();
    	assertNotNull(user.getEmpId());
    	assertNull(user.getId());
    	userRepository.findByEmpId(user.getEmpId());
    	assertNotNull(user.getRole());
    	assertNotNull(user.getUsername());
    	
    }
   
	@Test
    public void updateUserInfoTest() {
		User user = getUserTestData();
    	assertNotNull(user.getEmpId());
    	assertNull(user.getId());
    	userRepository.updateUserInfo(user.getUsername(),user.getPasswordHash(),user.getRole());
    	assertNotNull(user.getRole());
    	assertNotNull(user.getUsername());
    	
    }
   
	
	private User getUserTestData(){
     return new User("testuser", "testuser", "USER","sp16902");
	}

}

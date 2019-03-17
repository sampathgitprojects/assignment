package com.assignment;

import org.junit.Test;
import org.junit.runner.*;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.model.User;
import com.assignment.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CrudbootApplicationTests {


	private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @Test
    public void addUser() {
    	User user = new User("testuser", "testuser", "USER","sp16902");

    	assertNull(user.getId());
    	userRepository.save(user);
    	assertNotNull(user.getId());
    }
    
}

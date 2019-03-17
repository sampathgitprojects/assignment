package com.assignment.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.model.User;




/**
 * @author sampath
 *
 * this is user repository class for all user related DB operations
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	User findByEmpId(String empId);

	@Modifying
	@Query("update User u set u.username = ?1, u.passwordHash = ?2 where u.role = ?3")
	void updateUserInfo(String userName, String password, String role);

}

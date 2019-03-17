package com.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.assignment.SpringbootApplication;
import com.assignment.model.User;
import com.assignment.repository.UserRepository;

/**
 * This class is used by spring controller to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private static Logger logger = LoggerFactory
			.getLogger(SpringbootApplication.class);

	private final UserRepository repository;

	@Autowired
	public UserDetailServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			User curruser = repository.findByUsername(username);

			return new org.springframework.security.core.userdetails.User(
					username, curruser.getPasswordHash(), true, true, true,
					true,
					AuthorityUtils.createAuthorityList(curruser.getRole()));
		} catch (Exception ex) {
			logger.error("exception occured while loading user by name");
			throw ex;
		}
	}

	public User getUserForEmpId(String empId) {
		User user = null;
		try {
			user = repository.findByEmpId(empId);
			if (StringUtils.isEmpty(user)) {
				return user;
			}
		} catch (Exception e) {
			logger.error("unable to get user information by empId");
		}
		return user;
	}

	public void updateUser(String userName, String password, String role) {
		repository.updateUserInfo(userName, password, role);
	}

	public Boolean deleteUser(String empId) {
		User user = null;
		try {
			user = repository.findByEmpId(empId);
			if (!StringUtils.isEmpty(user)) {
				repository.deleteById(user.getId());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("unable to get user information by empId");
			throw e;
		}
	}

}
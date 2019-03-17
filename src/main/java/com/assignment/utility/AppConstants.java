package com.assignment.utility;


/**
 * @author sampath
 * this class is created for constants used in entire application
 */
public final class AppConstants {

	private AppConstants() {
		throw new IllegalStateException("this is utility class");
	}

	public static final String LOGIN_REDIRECT = "redirect:/login";
	public static final String SIGN_UP = "signup";
	public static final String CRETAE = "create";
	public static final String GET_USERS = "getUsers";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String INDEX = "index";
}

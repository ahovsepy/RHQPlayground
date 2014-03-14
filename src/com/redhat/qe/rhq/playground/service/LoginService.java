package com.redhat.qe.rhq.playground.service;

import com.redhat.qe.rhq.playground.model.User;

public class LoginService {

	private User user;

	public LoginService(String userName, String password) {
		user = new User();
		user.setPassword(password);
		user.setUserName(userName);
	}

	public boolean isValidUser(String userName, String password) {
		if (userName != null && password !=null && !userName.isEmpty() && !password.isEmpty()) {
			return true;
		}
		return false;
	}

	public User getUser() {
		return user;
	}
}

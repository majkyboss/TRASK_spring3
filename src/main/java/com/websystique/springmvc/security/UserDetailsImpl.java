package com.websystique.springmvc.security;

import org.springframework.security.core.authority.AuthorityUtils;

import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.model.User;

public class UserDetailsImpl extends
		org.springframework.security.core.userdetails.User {

	private User user;

	public UserDetailsImpl(User user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils
				.createAuthorityList(user.getRole().getName()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public int getId() {
		return user.getId();
	}

	public Role getRole() {
		return user.getRole();
	}

}

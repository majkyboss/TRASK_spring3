package com.websystique.springmvc.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.security.UserDetailsImpl;

@Service("userDetailService")
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		com.websystique.springmvc.model.User user = userDao
				.findByUserName(username);
		HashSet<Role> roleSet = new HashSet<Role>();
		roleSet.add(user.getRole());
		List<GrantedAuthority> authorities = buildUserAuthority(roleSet);

		return buildUserForAuthentication(user, authorities);
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private UserDetailsImpl buildUserForAuthentication(
			com.websystique.springmvc.model.User user,
			List<GrantedAuthority> authorities) {
		// return new User(user.getUsername(), user.getPassword(),
		// user.isEnabled(), true, true, true, authorities);
		return new UserDetailsImpl(user);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		List<GrantedAuthority> Result = new LinkedList<GrantedAuthority>(
				setAuths);

		return Result;
	}

}

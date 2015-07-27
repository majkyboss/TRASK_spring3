package com.websystique.springmvc.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.websystique.springmvc.security.UserDetailsImpl;

@ControllerAdvice
public class CurrentUserControllerAdvice {

	@ModelAttribute("currentUser")
	public UserDetailsImpl getCurrentUser(Authentication authentication) {
		return (authentication == null) ? null
				: (UserDetailsImpl) authentication.getPrincipal();
	}

}

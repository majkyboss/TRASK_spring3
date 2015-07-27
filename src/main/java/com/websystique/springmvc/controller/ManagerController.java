package com.websystique.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.websystique.springmvc.model.Registration;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.security.UserDetailsImpl;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController extends UserController {

	private static final String JSP_PAGE_USERS_LIST = "usersList";

	@Autowired
	UserService userService;

	/**
	 * Gets the registrations of all manager's agents
	 */
	@Override
	protected List<Registration> getRegistrations() {
		// TODO find the way how to get the current logged user (manager id)
		int managerId = 0;
		List<Registration> regs = regsService.findAllByManagerId(managerId);
		// TODO delete
		regs = regsService.findAllRegistrations();

		return regs;
		// return super.getRegistrations();
	}

	@RequestMapping("/show_users_list")
	public String showUsers(ModelMap model) {
		loadUsersToPageAsList(model);

		return JSP_PAGE_USERS_LIST;
	}

	protected void loadUsersToPageAsList(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl userDetail = (UserDetailsImpl) auth.getPrincipal();

			List<User> users = userService
					.findAllUsersByBranchManagerId(userDetail.getId());
			// TODO delete
			users = userService.findAllUsers();

			model.addAttribute("users", users);
		}

	}
}

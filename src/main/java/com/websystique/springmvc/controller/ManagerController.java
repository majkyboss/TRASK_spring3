package com.websystique.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.websystique.springmvc.model.Registration;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.RegistrationService;
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
		//TODO find the way how to get the current logged user (manager id)
		int managerId = 0;
		return regsService.findAllByManagerId(managerId);
		// return super.getRegistrations();
	}
	
	@RequestMapping("/show_subs")
	public String showSubUsers(ModelMap model){
		//TODO find the way how to get the current logged user (manager id)
		int managerId = 0;
		List<User> users = userService.findAllUsersByUnitManagerId(managerId);
		
		model.addAttribute("users", users);
		
		return JSP_PAGE_USERS_LIST;
	}
}

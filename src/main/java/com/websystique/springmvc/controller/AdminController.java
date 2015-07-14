package com.websystique.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.RegStatus;
import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.RegStatusService;
import com.websystique.springmvc.service.RoleService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends UserController {
	private static final String JSP_PAGE_USERS_LIST = "usersList";
	private static final String JSP_PAGE_USER_DETAIL_FORM = "editUser";
	private static final String JSP_PAGE_BRANCH_DETAIL_FORM = "editBranch";

	@Autowired
	RegStatusService statusService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	// approve_reg
	// add_user
	// edit_user (..., set role)
	// create_unit
	// edit_unit (add/remove users, set manager)

	// TODO check if the url is working
	@Override
	public String editRegistration(ModelMap model, String ico,
			String regDateString) {
		// load all statuses
		List<RegStatus> statuses = statusService.findAllStatuses();
		model.addAttribute("statuses", statuses);

		return super.editRegistration(model, ico, regDateString);
	}

	// show users, add users, edit users, remove users

	@RequestMapping("/create_user")
	public String createUser(ModelMap model) {
		User newUser = new User();
		model.addAttribute("user", newUser);
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("roles", roles);
		return JSP_PAGE_USER_DETAIL_FORM;
	}

	@RequestMapping(value = { "/save_user" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {
		if (result.hasErrors())
			return JSP_PAGE_USER_DETAIL_FORM;

		userService.saveUser(user);

		model.addAttribute("success", "User (" + user.getBirthNumber()
				+ ") was created");
		return showUsers(model);
	}

	@RequestMapping("/edit_user_{userId}")
	public String editUser(ModelMap model, @PathVariable int userId) {
		User user = userService.findUserById(userId);
		if (user != null){
			model.addAttribute("user", user);
			List<Role> roles = roleService.findAllRoles();
			model.addAttribute("roles", roles);
		}

		return JSP_PAGE_USER_DETAIL_FORM;
	}
	
	@RequestMapping("/del_user_{userId}")
	public String deleteUser(ModelMap model, @PathVariable int userId){
		User user = userService.findUserById(userId);
		
		userService.deleteUser(userId);
		
		model.addAttribute("success", "User (" + user.getBirthNumber()
				+ ") was deleted");
		return showUsers(model);
	}

	@RequestMapping("/show_users_list")
	public String showUsers(ModelMap model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);

		return JSP_PAGE_USERS_LIST;
	}
	
	@RequestMapping("/create_branch")
	public String createBranch(ModelMap model){
		Branch newBranch = new Branch();
		model.addAttribute("branch", newBranch);
		return JSP_PAGE_BRANCH_DETAIL_FORM;
	}
	
	@RequestMapping("save_branch")
	public String saveBranch(ModelMap model){
		
		return "";
	}
	
	// TODO find the way how to add user to unit (branch), manager
	
	public String showBranches(){
		
		return "";
	}
}

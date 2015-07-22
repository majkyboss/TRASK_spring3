package com.websystique.springmvc.controller;

import java.beans.PropertyEditorSupport;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.websystique.springmvc.utils.HbUtils;

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

	@Override
	public String editRegistration(ModelMap model, @PathVariable String ico,
			@PathVariable String regDateString) {
		// load all statuses
		List<RegStatus> statuses = statusService.findAllStatuses();
		// TODO delete
		RegStatus s = new RegStatus();
		s.setId(0);
		s.setName("waiting");
		statuses.add(s);
		s = new RegStatus();
		s.setId(0);
		s.setName("approved");
		statuses.add(s);
		s = new RegStatus();
		s.setId(0);
		s.setName("end");
		statuses.add(s);

		model.addAttribute("statuses", statuses);

		// load all users
		List<User> users = userService.findAllUsers();

		// TODO delete
		User u = new User();
		u.setName("user1");
		users.add(u);
		u = new User();
		u.setName("user2");
		users.add(u);

		model.addAttribute("users", users);

		return super.editRegistration(model, ico, regDateString);
	}

	@RequestMapping("/create_user")
	public String createUser(@ModelAttribute("user") User user, ModelMap model) {
		//User user = new User();
		Role r = new Role();
		r.setId(5);
		r.setName("testROle");
		user.setRole(r);
		model.addAttribute("user", user);
		Map<String, Role> roles = new TreeMap<String, Role>();
		for (Role role : roleService.findAllRoles()) {
			roles.put(role.getId()+"", role);
		}
		
		model.addAttribute("roles", roles);
		return JSP_PAGE_USER_DETAIL_FORM;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Role.class, "role", new PropertyEditorSupport(){
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				//super.setAsText(text);
				int roleId = Integer.parseInt(text);
				Role role = roleService.findRoleById(roleId);
				setValue(role);
			}
		});
	}

	@RequestMapping(value = { "/save_user" }, method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result,
			ModelMap model) {
		if (result.hasErrors())
			return JSP_PAGE_USER_DETAIL_FORM;

		userService.saveUser(user);

//		model.addAttribute("success", "User (" + user.getBirthNumber()
//				+ ") was created");
		loadUsersToPage(model);
		return "redirect:show_users_list";
	}

	@RequestMapping("/edit_user_{userId}")
	public String editUser(ModelMap model, @PathVariable int userId) {
		User user = userService.findById(userId);
		if (user != null) {
			model.addAttribute("user", user);
			TreeMap<String, Role> roles = new TreeMap<String, Role>();
			for (Role role : roleService.findAllRoles()) {
				roles.put(role.getId()+"", role);
			}
			model.addAttribute("roles", roles);
		}

		return JSP_PAGE_USER_DETAIL_FORM;
	}

	@RequestMapping("/del_user_{userId}")
	public String deleteUser(ModelMap model, @PathVariable int userId) {
		User user = userService.findById(userId);

		userService.deleteUser(user);

//		model.addAttribute("success", "User (" + user.getBirthNumber()
//				+ ") was deleted");
		loadUsersToPage(model);
		return "redirect:show_users_list";
	}

	@RequestMapping("/show_users_list")
	public String showUsers(ModelMap model) {
		loadUsersToPage(model);

		return JSP_PAGE_USERS_LIST;
	}

	private void loadUsersToPage(ModelMap model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
	}

	// TODO branches work

	@RequestMapping("/create_branch")
	public String createBranch(ModelMap model) {
		Branch newBranch = new Branch();
		model.addAttribute("branch", newBranch);
		return JSP_PAGE_BRANCH_DETAIL_FORM;
	}

	@RequestMapping("/save_branch")
	public String saveBranch(ModelMap model) {

		return "";
	}

	// find the way how to add user to unit (branch), manager

	public String showBranches() {

		return "";
	}
}

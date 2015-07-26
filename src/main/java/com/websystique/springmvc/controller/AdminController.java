package com.websystique.springmvc.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.RegStatus;
import com.websystique.springmvc.model.Registration;
import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.RegStatusService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends UserController {
	private static final String JSP_PAGE_USERS_LIST = "usersList";
	private static final String JSP_PAGE_USER_DETAIL_FORM = "editUser";
	private static final String JSP_PAGE_BRANCH_DETAIL_FORM = "editBranch";
	private static final String JSP_PAGE_BRANCHES_LIST = "branchesList";
	private static final String JSP_PAGE_BRANCH_ADD_AGENT = "addAgentToBranch";

	@Autowired
	RegStatusService statusService;
	@Autowired
	UserService userService;

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editRegistration(ModelMap model, @PathVariable String ico,
			@PathVariable String regDateString) {
		// load all statuses
		LoadStatusesToPageAsMap(model);

		// load all users
		loadUsersToPageAsMap(model);

		return super.editRegistration(model, ico, regDateString);
	}

	@Override
	public String saveRegistration(Registration registration,
			BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			LoadStatusesToPageAsMap(model);

			loadUsersToPageAsMap(model);
		}

		return super.saveRegistration(registration, result, model);
	}

	@RequestMapping("/create_user")
	public String createUser(@ModelAttribute("user") User user, ModelMap model) {
		// User user = new User();
		model.addAttribute("user", user);
		Map<String, Role> roles = new TreeMap<String, Role>();
		for (Role role : roleService.findAllRoles()) {
			roles.put(role.getId() + "", role);
		}

		model.addAttribute("roles", roles);
		return JSP_PAGE_USER_DETAIL_FORM;
	}

	@RequestMapping(value = { "/save_user" }, method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute("user") User user,
			BindingResult result, ModelMap model) {
		if (result.hasErrors())
			return JSP_PAGE_USER_DETAIL_FORM;

		userService.saveUser(user);

		return "redirect:show_users_list";
	}

	@RequestMapping("/edit_user_{userId}")
	public String editUser(ModelMap model, @PathVariable int userId) {
		User user = userService.findById(userId);
		if (user != null) {
			model.addAttribute("user", user);
			TreeMap<String, Role> roles = new TreeMap<String, Role>();
			for (Role role : roleService.findAllRoles()) {
				roles.put(role.getId() + "", role);
			}
			model.addAttribute("roles", roles);
		}

		return JSP_PAGE_USER_DETAIL_FORM;
	}

	@RequestMapping("/del_user_{userId}")
	public String deleteUser(ModelMap model, @PathVariable int userId) {
		User user = userService.findById(userId);

		try {
			userService.deleteUser(user);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}

		return "redirect:show_users_list";
	}

	@RequestMapping("/show_users_list")
	public String showUsers(ModelMap model) {
		loadUsersToPageAsList(model);

		return JSP_PAGE_USERS_LIST;
	}

	private void loadUsersToPageAsList(ModelMap model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
	}

	private void loadUsersToPageAsMap(ModelMap model) {
		// List<User> users = userService.findAllUsers();
		Map<String, User> users = new TreeMap<String, User>();
		for (User user : userService.findAllUsers()) {
			users.put(user.getId() + "", user);
		}
		model.addAttribute("users", users);
	}

	private void LoadStatusesToPageAsMap(ModelMap model) {
		Map<String, RegStatus> statuses = new TreeMap<String, RegStatus>();
		for (RegStatus status : statusService.findAllStatuses()) {
			statuses.put(status.getId() + "", status);
		}
		model.addAttribute("statuses", statuses);
	}

	@RequestMapping("/create_branch")
	public String createBranch(ModelMap model) {
		Branch newBranch = new Branch();
		model.addAttribute("branch", newBranch);

		loadUsersToPageAsMap(model);

		model.addAttribute("agents", new TreeSet<User>());

		return JSP_PAGE_BRANCH_DETAIL_FORM;
	}

	@RequestMapping(value = { "/save_branch" }, method = RequestMethod.POST)
	public String saveBranch(@Valid @ModelAttribute("branch") Branch branch,
			ModelMap model, BindingResult result) {

		if (result.hasErrors()) {
			return JSP_PAGE_BRANCH_DETAIL_FORM;
		}

		branchService.saveBranch(branch);

		return showBranches(model);
	}

	@RequestMapping("/edit_branch_{branchId}")
	public String editBranch(@PathVariable int branchId, ModelMap model) {
		// name, manager
		Branch branch = branchService.findById(branchId);
		model.addAttribute("branch", branch);

		loadUsersToPageAsMap(model);

		return JSP_PAGE_BRANCH_DETAIL_FORM;
	}

	@RequestMapping("/show_branches_list")
	public String showBranches(ModelMap model) {
		List<Branch> branches = branchService.findAllBranches();
		model.addAttribute("branches", branches);

		return JSP_PAGE_BRANCHES_LIST;
	}

	@RequestMapping("/add_user_to_branch_{branchId}")
	public String addUserToBranch(@PathVariable int branchId, ModelMap model) {

		Branch branch = branchService.findById(branchId);
		model.addAttribute("branch", branch);

		loadUsersToPageAsMap(model);

		return JSP_PAGE_BRANCH_ADD_AGENT;
	}

	@RequestMapping(value = { "/update_branch_users" }, method = RequestMethod.POST)
	public String updateBranchUsers(
			@Valid @ModelAttribute("branch") Branch branch, ModelMap model,
			BindingResult result) {

		if (result.hasErrors()) {
			return JSP_PAGE_BRANCH_DETAIL_FORM;
		}

		branchService.saveBranch(branch);

		return showBranches(model);
	}

	@RequestMapping("/del_branch_{branchId}")
	public String deleteBranch(@PathVariable int branchId, ModelMap model) {
		Branch b = branchService.findById(branchId);
		branchService.deleteBranch(b);

		return showBranches(model);
	}
}

package com.websystique.springmvc.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.RegStatus;
import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.model.Unit;
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
	public String editRegistration(ModelMap model, @PathVariable String ico,
			@PathVariable String regDateString) {
		// load all statuses
		List<RegStatus> statuses = statusService.findAllStatuses();

		model.addAttribute("statuses", statuses);

		// load all users
		loadUsersToPageAsList(model);

		return super.editRegistration(model, ico, regDateString);
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

		// model.addAttribute("success", "User (" + user.getBirthNumber()
		// + ") was created");
		loadUsersToPageAsList(model);
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

		userService.deleteUser(user);

		// model.addAttribute("success", "User (" + user.getBirthNumber()
		// + ") was deleted");
		loadUsersToPageAsList(model);
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

		Branch tempBranche = branchService.findById(branch.getId());
		tempBranche.setAgentUnits(branch.getAgentUnits());

		List<User> unitUsers = userService.findAllUsersInUnit(branch.getId());
		List<int[]> toDel = new LinkedList<int[]>();
		for (User user : unitUsers) {
			boolean keep = false;
			for (Unit unit : branch.getAgentUnits()) {
				if (unit.getUser().getId() == user.getId()) {
					keep = true;
					break;
				}
			}
			if (!keep) {
				toDel.add(new int[] { user.getId(), branch.getId() });
			}

		}

		branchService.saveBranch(tempBranche);

		for (int[] d : toDel) {
			unitService.deleteById(d[0], d[1]);
		}

		return showBranches(model);
	}
}

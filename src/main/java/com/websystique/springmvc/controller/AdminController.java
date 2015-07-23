package com.websystique.springmvc.controller;

import java.beans.PropertyEditorSupport;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.ClassArrayEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
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
import com.websystique.springmvc.model.Unit;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.BranchService;
import com.websystique.springmvc.service.RegStatusService;
import com.websystique.springmvc.service.RoleService;
import com.websystique.springmvc.service.UnitService;
import com.websystique.springmvc.service.UserService;
import com.websystique.springmvc.utils.HbUtils;

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
	@Autowired
	RoleService roleService;
	@Autowired
	BranchService branchService;
	@Autowired
	UnitService unitService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Role.class, "role",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						// super.setAsText(text);
						int roleId = Integer.parseInt(text);
						Role role = roleService.findRoleById(roleId);
						setValue(role);
					}
				});
		binder.registerCustomEditor(User.class, "manager",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						int userId = Integer.parseInt(text);
						User user = userService.findById(userId);
						setValue(user);
					}
				});
		
		binder.registerCustomEditor(TreeSet.class, "agentUnits", new CustomCollectionEditor(Set.class){
			@Override
			protected Object convertElement(Object element) {
				if (element instanceof String) {
					String[] ids = ((String) element).split("\\_");
					
					int agentId = Integer.parseInt(ids[0]);
					User user = userService.findById(agentId);
					
					int branchId = Integer.parseInt(ids[1]);
					Unit unit = unitService.findById(agentId, branchId);
					if (unit==null) {
						unit = new Unit();
						unit.setUser(user);
						Branch b = branchService.findById(branchId);
						unit.setBranch(b);
						unitService.saveUnit(unit);
					}
					
					return unit;
				}
				
				return null;
			}
		});
//		binder.registerCustomEditor(Set.class, "agentUnits", new StringArrayPropertyEditor(","){
//
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				int agentId = Integer.parseInt(text);
//				Unit unit = unitService.findById(agentId);
//				setValue(unit);
//			}
//			
//		});
		
	}

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
	public String addUserToBranch(@PathVariable int branchId,
			ModelMap model) {
		
		Branch branch = branchService.findById(branchId);
		model.addAttribute("branch", branch);
		
		loadUsersToPageAsMap(model);

		return JSP_PAGE_BRANCH_ADD_AGENT;
	}
	
	@RequestMapping(value = { "/update_branch_users" }, method = RequestMethod.POST)
	public String updateBranchUsers(@Valid @ModelAttribute("branch") Branch branch,
			ModelMap model, BindingResult result) {

		if (result.hasErrors()) {
			return JSP_PAGE_BRANCH_DETAIL_FORM;
		}
		
		Branch tempBranche = branchService.findById(branch.getId());
		tempBranche.setAgentUnits(branch.getAgentUnits());

		branchService.saveBranch(tempBranche);

		return showBranches(model);
	}
}

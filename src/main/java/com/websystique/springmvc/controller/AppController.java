package com.websystique.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.Employee;
import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.model.Unit;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.BranchService;
import com.websystique.springmvc.service.EmployeeService;
import com.websystique.springmvc.service.RoleService;
import com.websystique.springmvc.service.UnitService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;

	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listEmployees(ModelMap model) {
		List<Employee> employees = employeeService.findAllEmployees();
		model.addAttribute("employees", employees);
		return "allemployees";
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "registrationEmployee";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registrationEmployee";
		}

		employeeService.saveEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName()
				+ " registered successfully");
		return "success";
	}

	/*
	 * This method will delete an employee by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn) {
		employeeService.deleteEmployeeBySsn(ssn);
		return "redirect:/list";
	}

	// --------------------------- testing -------------------------------------

	@RequestMapping(value = { "/hibernate_test" }, method = RequestMethod.GET)
	public String hibernateTest() {

		Role adminRole = new Role();
		adminRole.setName("admin");
		roleService.saveRole(adminRole);

		Role userRole = new Role();
		userRole.setName("user");
		roleService.saveRole(userRole);

		User user1 = new User();
		user1.setName("user1");
		user1.setLastName("last1");
		user1.setBirthNumber("1111111111");
		user1.setDateIn(LocalDate.now());
		user1.setDateOut(user1.getDateIn().plusMonths(6));

		user1.setRole(adminRole);

		userService.saveUser(user1);

		return "redirect:/";
	}

	@Autowired
	BranchService branchService;
	@Autowired
	UnitService unitService;

	@RequestMapping(value = { "/unit_test" }, method = RequestMethod.GET)
	public void unitTest() {
		Role role = new Role();
		role.setName("manager");
		roleService.saveRole(role);

		User user = new User();
		user.setName("manager1");
		user.setBirthNumber("1111111111");
		user.setDateIn(LocalDate.now());
		user.setRole(role);
		userService.saveUser(user);

		Branch branch = new Branch();
		branch.setManager(user);
		branch.setName("unit1");

		branchService.saveBranch(branch);

		Unit unit = new Unit();
		unit.setUser(user);
		unit.setBranch(branch);

		unitService.saveUnit(unit);

		User user1 = new User();
		user1.setName("user1");
		user1.setLastName("last1");
		user1.setBirthNumber("1111111111");
		user1.setDateIn(LocalDate.now());
		user1.setDateOut(user1.getDateIn().plusMonths(6));
		user1.setRole(role);
		userService.saveUser(user1);

		Unit unit2 = new Unit();
		unit2.setUser(user1);
		unit2.setBranch(branch);
		unitService.saveUnit(unit2);
	}
	
	@RequestMapping("/freemarker")
	public String freemarkerTest(ModelMap model){
		model.addAttribute("stringAttr", "ahoj");
		System.out.println("controller reached");
		
		return "freemarkerTest";
	}
	
	@RequestMapping("/basic")
	public String basicTemplate(){
		return "basicTemplate";
	}
}

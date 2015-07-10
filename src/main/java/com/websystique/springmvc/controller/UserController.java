package com.websystique.springmvc.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.websystique.springmvc.model.Registration;

@Controller
@RequestMapping("/user")
public class UserController {

	// create_reg
	// edit_reg
	// show_regs_list
	// reg_details
	// add_note

	@RequestMapping(value = { "/create_reg" })
	public String createRegistration(ModelMap model) {
		Registration newReg = new Registration();
		newReg.setCompanyName("test name");
		model.addAttribute("newRegistration", newReg);
		return "registration";
	}

	public String saveRegistration(@Valid Registration registration,
			BindingResult result, ModelMap model) {

		return null;
	}
}

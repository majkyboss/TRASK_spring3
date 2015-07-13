package com.websystique.springmvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.websystique.springmvc.model.Registration;

@Controller
@RequestMapping("/manager")
public class ManagerController extends UserController {

	@Override
	protected List<Registration> getRegistrations() {
		
		return super.getRegistrations();
	}
}

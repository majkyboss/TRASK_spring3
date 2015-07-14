package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Role;

public interface RoleService {
	void saveRole(Role adminRole);

	List<Role> findAllRoles();

}

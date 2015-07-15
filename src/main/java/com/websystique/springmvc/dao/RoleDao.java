package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.Role;

public interface RoleDao {

	void saveRole(Role role);

	List<Role> findAllRoles();

}

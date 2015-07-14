package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.AbstractDao;
import com.websystique.springmvc.dao.RoleDao;
import com.websystique.springmvc.model.Role;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	@Qualifier("roleDao")
	private RoleDao dao;

	@Override
	public void saveRole(Role role) {
		dao.saveRole(role);
	}

	@Override
	public List<Role> findAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}

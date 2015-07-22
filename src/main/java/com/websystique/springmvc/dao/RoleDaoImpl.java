package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao implements RoleDao {

	@Override
	public void saveRole(Role role) {
		persist(role);
	}

	@Override
	public List<Role> findAllRoles() {
		Criteria criteria = getSession().createCriteria(Role.class);
		return criteria.list();
	}

	@Override
	public Role findRoleById(int roleId) {
		// return (User) getSession().get(User.class, userId);
		return (Role) getSession().get(Role.class, roleId);
	}

}

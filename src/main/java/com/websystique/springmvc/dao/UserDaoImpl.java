package com.websystique.springmvc.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Registration;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.utils.HbUtils;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@Override
	public void saveUser(User user) {
		persist(user);
	}

	@Override
	public List<User> findAllUsers() {
		Criteria criteria = getSession().createCriteria(User.class);

		//use join via criteria
		//criteria.createAlias("role", "role_id", JoinType.INNER_JOIN);
		// or set relation to FetchType.EAGER
		return criteria.list();
	}

	@Override
	public User findById(int userId) {
		return (User) getSession().get(User.class, userId);
	}

	@Override
	public List<User> findAllByUnitManager(int managerId) {
		// TODO join via unit and branch
		return null;
	}

	@Override
	public void deleteUser(User user) {
		delete(user);
	}

}

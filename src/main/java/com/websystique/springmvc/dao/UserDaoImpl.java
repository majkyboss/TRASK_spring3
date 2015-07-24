package com.websystique.springmvc.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Unit;
import com.websystique.springmvc.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@Autowired
	@Qualifier("unitDao")
	UnitDao unitDao;

	@Override
	public void saveUser(User user) {
		persist(user);
	}

	@Override
	public List<User> findAllUsers() {
		Criteria criteria = getSession().createCriteria(User.class);

		// use join via criteria
		// criteria.createAlias("role", "role_id", JoinType.INNER_JOIN);
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

	@Override
	public List<User> findAllUsersInUnits() {
		List<Unit> units = unitDao.findAllUnits();
		List<User> users = new LinkedList<User>();
		for (Unit unit : units) {
			if (!users.contains(unit.getUser())) {
				users.add(unit.getUser());
			}
		}

		return users;
	}

	@Override
	public List<User> findAllUsersInUnit(int branchId) {
		List<Unit> units = unitDao.findAllByBranchId(branchId);
		List<User> users = new LinkedList<User>();
		for (Unit unit : units) {
			if (!users.contains(unit.getUser())) {
				users.add(unit.getUser());
			}
		}

		return users;
	}

}

package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

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
		// or use Hibernate.initialize(object);
		return criteria.list();
	}

	@Override
	public User findById(int userId) {
		return (User) getSession().get(User.class, userId);
	}

	@Override
	public List<User> findAllByBranchManager(int managerId) {
		Criteria criteria = getSession().createCriteria(User.class)
				.createCriteria("currentBranch").createCriteria("manager")
				.add(Restrictions.eq("id", managerId));

		List<User> users = criteria.list();

		for (User user : users) {
			Hibernate.initialize(user.getCurrentBranch());
			Hibernate.initialize(user.getRole());
		}

		return users;
	}

	@Override
	public void deleteUser(User user) {
		delete(user);
	}

	@Override
	public User findByUserName(String username) {
		Criteria criteria = getSession().createCriteria(User.class).add(
				Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}

}

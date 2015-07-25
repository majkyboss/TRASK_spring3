package com.websystique.springmvc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.BranchDao;
import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao dao;
	
	@Autowired
	@Qualifier("branchDao")
	private BranchDao branchDao;

	@Override
	public void saveUser(User user) {
		dao.saveUser(user);
	}

	@Override
	public List<User> findAllUsersByUnitManagerId(int managerId) {
		return dao.findAllByUnitManager(managerId);
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users = dao.findAllUsers();
		for (User user : users) {
			Hibernate.initialize(user.getRole());
		}
		return users;
	}

	@Override
	public User findById(int userId) {
		User user = dao.findById(userId);
		Hibernate.initialize(user.getRole());
		Hibernate.initialize(user.getCurrentBranch());
		return user;
	}

	@Override
	public void deleteUser(User user) {
		dao.deleteUser(user);
	}

	@Override
	public List<User> findAllUsersInBranches() {
		return getUsersFromBranches(branchDao.findAllBranches());
	}

	private List<User> getUsersFromBranches(List<Branch> branches) {
		Set<User> agents = new TreeSet<User>();
		
		for (Branch branch : branches) {
			Hibernate.initialize(branch.getAgents());
			agents.addAll(branch.getAgents());
		}
		
		return new LinkedList<User>(agents);
	}

	@Override
	public List<User> findAllUsersInBranch(int branchId) {
		List<Branch> branches = new LinkedList<Branch>();
		branches.add(branchDao.findById(branchId));
		return getUsersFromBranches(branches);
	}

}

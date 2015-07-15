package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao dao;

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
		return dao.findAllUsers();
	}

	@Override
	public User findById(int userId) {
		return dao.findById(userId);
	}

	@Override
	public void deleteUser(User user) {
		dao.deleteUser(user);
	}

}

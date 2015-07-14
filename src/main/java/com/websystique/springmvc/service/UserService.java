package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.User;

public interface UserService {
	void saveUser(User user1);

	List<User> findAllUsersByUnitManagerId(int managerId);

	List<User> findAllUsers();

	User findUserById(int userId);

	void deleteUser(int userId);
}

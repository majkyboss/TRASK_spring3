package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.User;

public interface UserService {
	void saveUser(User user1);

	List<User> findAllUsersByBranchManagerId(int managerId);

	List<User> findAllUsers();

	User findById(int userId);

	void deleteUser(User user);

	List<User> findAllUsersInBranches();

	List<User> findAllUsersInBranch(int id);
}

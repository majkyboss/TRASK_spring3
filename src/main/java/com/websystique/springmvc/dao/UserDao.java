package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.User;

public interface UserDao {

	void saveUser(User user);

	List<User> findAllUsers();

	User findById(int userId);

	List<User> findAllByUnitManager(int managerId);

	void deleteUser(User user);

	User findByEmail(String email);
}

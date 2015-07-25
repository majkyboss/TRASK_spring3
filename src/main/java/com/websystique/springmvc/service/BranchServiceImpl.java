package com.websystique.springmvc.service;

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

@Service("branchService")
@Transactional
public class BranchServiceImpl implements BranchService {

	@Autowired
	@Qualifier("branchDao")
	private BranchDao dao;

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public void saveBranch(Branch branch) {
		// update current branch in DB because it can not be loaded two objects
		// with same key in one transaction

		// reset refs from unset agents to this branch
		Set<User> newUsersToStore = new TreeSet<User>(branch.getAgents());

		Branch oldBranch = dao.findById(branch.getId());
		if (oldBranch != null) {
			for (User oldUser : oldBranch.getAgents()) {
				boolean contains = false;
				for (User newUser : branch.getAgents()) {
					if (newUser.getId() == oldUser.getId()) {
						contains = true;
						break;
					}
				}
				if (!contains) {
					oldUser.setCurrentBranch(null);
					userDao.saveUser(oldUser);
				} else {
					newUsersToStore.remove(oldUser);
				}
			}
			// update other values
			oldBranch.setAgents(branch.getAgents());
			oldBranch.setManager(branch.getManager());
			oldBranch.setName(branch.getName());
		} else {
			oldBranch = branch;
		}

		dao.saveBranch(oldBranch);
		// if will be problem to store the user - diff object with same id - add
		// actual
		// users into branch above
		for (User user : newUsersToStore) {
			user.setCurrentBranch(branch);
			userDao.saveUser(user);
		}
	}

	@Override
	public List<Branch> findAllBranches() {
		List<Branch> branches = dao.findAllBranches();
		for (Branch branch : branches) {
			Hibernate.initialize(branch.getAgents());
			Hibernate.initialize(branch.getManager());
		}
		return branches;
	}

	@Override
	public Branch findById(int branchId) {
		Branch branch = dao.findById(branchId);
		Hibernate.initialize(branch.getAgents());
		Hibernate.initialize(branch.getManager());
		return branch;
	}

	@Override
	public void deleteBranch(Branch branch) {
		for (User user : branch.getAgents()) {
			user.setCurrentBranch(null);
			userDao.saveUser(user);
		}
		dao.deleteBranch(branch);
	}

}

package com.websystique.springmvc.dao;

import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Branch;

@Repository("branchDao")
public class BranchDaoImpl extends AbstractDao implements BranchDao {

	@Override
	public void saveBranch(Branch branch) {
		persist(branch);
	}

}

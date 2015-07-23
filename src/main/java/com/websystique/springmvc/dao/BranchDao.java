package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.Branch;

public interface BranchDao {

	void saveBranch(Branch branch);

	List<Branch> findAllBranches();

	Branch findById(int branchId);

}

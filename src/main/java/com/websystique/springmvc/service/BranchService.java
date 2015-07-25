package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Branch;

public interface BranchService {

	void saveBranch(Branch branch);

	List<Branch> findAllBranches();

	Branch findById(int branchId);

	void deleteBranch(Branch b);

}

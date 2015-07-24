package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.BranchDao;
import com.websystique.springmvc.model.Branch;

@Service("branchService")
@Transactional
public class BranchServiceImpl implements BranchService {

	@Autowired
	@Qualifier("branchDao")
	private BranchDao dao;

	@Override
	public void saveBranch(Branch branch) {
		dao.saveBranch(branch);
	}

	@Override
	public List<Branch> findAllBranches() {
		return dao.findAllBranches();
	}

	@Override
	public Branch findById(int branchId) {
		return dao.findById(branchId);
	}

}

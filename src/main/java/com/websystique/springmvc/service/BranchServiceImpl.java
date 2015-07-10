package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.AbstractDao;
import com.websystique.springmvc.model.Branch;

@Service("branchService")
@Transactional
public class BranchServiceImpl implements BranchService {

	@Autowired
	private AbstractDao dao;

	@Override
	public void saveBranch(Branch branch) {
		dao.persist(branch);
	}

}

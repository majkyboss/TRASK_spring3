package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Branch;

@Repository("branchDao")
public class BranchDaoImpl extends AbstractDao implements BranchDao {

	@Override
	public void saveBranch(Branch branch) {
		persist(branch);
	}

	@Override
	public List<Branch> findAllBranches() {
		Criteria criteria = getSession().createCriteria(Branch.class);
		return criteria.list();
	}

	@Override
	public Branch findById(int branchId) {
		return (Branch) getSession().get(Branch.class, branchId);
	}

}

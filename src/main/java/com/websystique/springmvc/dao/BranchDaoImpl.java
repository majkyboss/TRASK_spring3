package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.User;

@Repository("branchDao")
public class BranchDaoImpl extends AbstractDao implements BranchDao {

	@Override
	public void saveBranch(Branch branch) {
		persist(branch);
	}

	@Override
	public List<Branch> findAllBranches() {
		Criteria criteria = getSession().createCriteria(Branch.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public Branch findById(int branchId) {
		return (Branch) getSession().get(Branch.class, branchId);
	}

	@Override
	public void deleteBranch(Branch branch) {
		delete(branch);
	}

	@Override
	public Branch findByAgentId(int agentId) {
		Query query = getSession().createQuery("SELECT branch FROM Branch AS branch JOIN User AS user WHERE :agentId IN element(branch.agents)");
		query.setParameter("agentId", agentId);		
		
		return (Branch) query.uniqueResult();
	}

}

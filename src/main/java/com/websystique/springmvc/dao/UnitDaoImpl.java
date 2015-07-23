package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Unit;

@Repository("unitDao")
public class UnitDaoImpl extends AbstractDao implements UnitDao {

	@Override
	public void saveUnit(Unit unit) {
		persist(unit);
	}

	@Override
	public Unit findById(int agentId, int branchId) {
//		Criteria criteria = getSession().createCriteria(Unit.class);
//		criteria.add(Restrictions.and(Restrictions.eq("user", agentId),
//				Restrictions.eq("branch", branchId)));
//		Object unit = criteria.uniqueResult();
//
//		return unit == null ? null : (Unit) (unit);
		
		org.hibernate.Query q = getSession().createQuery("SELECT unit FROM Unit As unit WHERE unit.user.id = :agentId AND unit.branch.id = :branchId");
		q.setParameter("agentId", agentId);
		q.setParameter("branchId", branchId);
		return (Unit) q.uniqueResult();
	}

	@Override
	public List<Unit> findAllByBranchId(int id) {
		Criteria criteria = getSession().createCriteria(Unit.class);
		criteria.add(Restrictions.eq("user.id", id));
		
		return criteria.list();
	}

}

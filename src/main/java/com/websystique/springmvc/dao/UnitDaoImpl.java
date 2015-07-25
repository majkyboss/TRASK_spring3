package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Branch;
import com.websystique.springmvc.model.Unit;
import com.websystique.springmvc.model.User;

@Repository("unitDao")
public class UnitDaoImpl extends AbstractDao implements UnitDao {

	@Override
	public void saveUnit(Unit unit) {
		persist(unit);
	}

	@Override
	public Unit findById(int agentId, int branchId) {
		// Criteria criteria = getSession().createCriteria(Unit.class);
		// criteria.add(Restrictions.and(Restrictions.eq("user", agentId),
		// Restrictions.eq("branch", branchId)));
		// Object unit = criteria.uniqueResult();
		//
		// return unit == null ? null : (Unit) (unit);

		org.hibernate.Query q = getSession()
				.createQuery(
						"SELECT unit FROM Unit As unit WHERE unit.user.id = :agentId AND unit.branch.id = :branchId");
		q.setParameter("agentId", agentId);
		q.setParameter("branchId", branchId);
		return (Unit) q.uniqueResult();
	}

	@Override
	public List<Unit> findAllByBranchId(int id) {
		Criteria criteria = getSession().createCriteria(Unit.class);
		criteria.add(Restrictions.eq("branch.id", id));

		return criteria.list();
	}

	@Override
	public Unit findByUserId(int agentId) {
		HibernateDaoSupport c = new HibernateDaoSupport() {
		};
		c.setSessionFactory(sessionFactory);
		Unit exampleUnit = new Unit();
		User u = new User();
		u.setId(agentId);
		exampleUnit.setUser(u);
		List<Unit> units = c.getHibernateTemplate().findByExample(exampleUnit);
		for (Unit unit : units) {
			c.getHibernateTemplate().initialize(unit.getBranch());
		}
		return units.size() > 0 ? units.get(0) : null;
	}

	@Override
	public List<Unit> findAllUnits() {
		Criteria criteria = getSession().createCriteria(Unit.class);
		return criteria.list();
	}

	@Override
	public void deleteById(int userId, int branchId) {
//		HibernateDaoSupport c = new HibernateDaoSupport() {
//		};
//		c.setSessionFactory(sessionFactory);
//		Unit exampleUnit = new Unit();
//		User u = new User();
//		u.setId(userId);
//		Branch b = new Branch();
//		b.setId(branchId);
//		exampleUnit.setUser(u);
//		exampleUnit.setBranch(b);
//		List<Unit> units = c.getHibernateTemplate().findByExample(exampleUnit);
//		if (units.size() > 0) {
//			delete(units.get(0));
//		}
	}

}

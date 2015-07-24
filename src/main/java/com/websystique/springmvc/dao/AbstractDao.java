package com.websystique.springmvc.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class AbstractDao extends HibernateDaoSupport {

//	@Autowired
//	protected SessionFactory sessionFactory;
	
	@Autowired
	public void init(SessionFactory factory){
		setSessionFactory(factory);
	}

//	protected Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}

	public void persist(Object entity) {
		// getSession().persist(entity);
//		getSession().saveOrUpdate(entity);
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void delete(Object entity) {
//		getSession().delete(entity);
		getHibernateTemplate().delete(entity);
	}
}

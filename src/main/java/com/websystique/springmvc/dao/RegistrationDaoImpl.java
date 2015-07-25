package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Registration;

@Repository("registrationDao")
public class RegistrationDaoImpl extends AbstractDao implements RegistrationDao {

	@Override
	public void saveRegistration(Registration registration) {
		persist(registration);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Registration> findAllRegistrations() {
		Criteria criteria = getSession().createCriteria(Registration.class);
		return (List<Registration>) criteria.list();
		// return regs;
	}

	@Override
	public Registration findByKey(String ico, LocalDate regDate) {
		Criteria criteria = getSession().createCriteria(Registration.class);
		criteria.add(Restrictions.and(Restrictions.eq("ico", ico),
				Restrictions.eq("regDate", regDate)));
		return (Registration) criteria.uniqueResult();
	}

	@Override
	public List<Registration> findAllByManagerId(int managerId) {
		Criteria criteria = getSession().createCriteria(Registration.class);
		// http://stackoverflow.com/questions/2347359/hibernate-createcriteria-or-createalias
		// TODO join via unit and branch
		return null;
	}

	@Override
	public void deleteRegistration(Registration registration) {
		getSession().delete(registration);
	}

}

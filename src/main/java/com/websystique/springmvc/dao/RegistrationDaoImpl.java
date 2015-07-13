package com.websystique.springmvc.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Qualifier;
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
	}

	@Override
	public void deleteRegistration(String ico, Date registrationDate) {
		// TODO Auto-generated method stub
		
	}

}

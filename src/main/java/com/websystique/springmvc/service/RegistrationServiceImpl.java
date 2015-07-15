package com.websystique.springmvc.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.RegistrationDao;
import com.websystique.springmvc.model.Registration;

@Service("regsService")
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	@Qualifier("registrationDao")
	private RegistrationDao dao;

	@Override
	public void saveRegistration(Registration registration) {
		dao.saveRegistration(registration);
	}

	@Override
	public List<Registration> findAllRegistrations() {
		return dao.findAllRegistrations();
	}

	@Override
	public void deleteRegistration(String ico, LocalDate registrationDate) {
		dao.deleteRegistration(ico, registrationDate);
	}

	@Override
	public boolean isRegistrationUnique(String ico, LocalDate regDate) {
		Registration reg = findByKey(ico, regDate);
		return (reg == null);
	}

	@Override
	public Registration findByKey(String ico, LocalDate regDate) {
		return dao.findByKey(ico, regDate);
	}

	@Override
	public List<Registration> findAllByManagerId(int managerId) {
		return dao.findAllByManagerId(managerId);
	}

}

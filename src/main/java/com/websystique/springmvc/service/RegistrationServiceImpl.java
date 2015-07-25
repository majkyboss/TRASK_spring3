package com.websystique.springmvc.service;

import java.util.List;

import org.hibernate.Hibernate;
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
		List<Registration> regs = dao.findAllRegistrations();
		for (Registration r : regs) {
			Hibernate.initialize(r.getRegistrator());
			Hibernate.initialize(r.getRegistratorBranch());
			Hibernate.initialize(r.getRegStatus());
		}
		return regs;
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
		Registration reg = dao.findByKey(ico, regDate);
		if (reg != null) {
			Hibernate.initialize(reg.getRegistrator());
			Hibernate.initialize(reg.getRegistratorBranch());
			Hibernate.initialize(reg.getRegStatus());
		}
		return reg;
	}

	@Override
	public List<Registration> findAllByManagerId(int managerId) {
		return dao.findAllByManagerId(managerId);
	}

	@Override
	public void deleteRegistration(Registration registration) {
		dao.deleteRegistration(registration);
	}

}

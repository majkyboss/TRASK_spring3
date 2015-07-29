package com.websystique.springmvc.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.NoteDao;
import com.websystique.springmvc.dao.RegistrationDao;
import com.websystique.springmvc.model.Note;
import com.websystique.springmvc.model.Registration;

@Service("regsService")
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	@Qualifier("registrationDao")
	private RegistrationDao dao;
	
	@Autowired
	@Qualifier("noteDao")
	private NoteDao noteDao;

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
			Hibernate.initialize(reg.getNotes());
		}
		return reg;
	}

	@Override
	public List<Registration> findAllByManagerId(int managerId) {
		List<Registration> regs = dao.findAllByManagerId(managerId);

		for (Registration registration : regs) {
			Hibernate.initialize(registration.getRegistrator());
			Hibernate.initialize(registration.getRegistratorBranch());
			Hibernate.initialize(registration.getRegStatus());
		}

		return regs;
	}

	@Override
	public void deleteRegistration(Registration registration) {
		for (Note note : registration.getNotes()) {
			noteDao.deleteNote(note);
		}
		dao.deleteRegistration(registration);
	}

}

package com.websystique.springmvc.service;

import java.util.Date;
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
	private RegistrationDao regDao;
	
	@Override
	public void saveRegistration(Registration registration) {
		regDao.saveRegistration(registration);
	}

	@Override
	public List<Registration> findAllRegistrations() {
		return regDao.findAllRegistrations();
	}

	@Override
	public void deleteRegistration(String ico, Date registrationDate) {
		regDao.deleteRegistration(ico, registrationDate);
	}

	@Override
	public boolean isRegistrationUnique(String ico, LocalDate reg_date) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Registration findByKey(String ico, LocalDate regDate) {
		// TODO Auto-generated method stub
		return null;
	}

}

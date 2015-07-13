package com.websystique.springmvc.dao;

import java.util.Date;
import java.util.List;

import com.websystique.springmvc.model.Registration;

public interface RegistrationDao {

	void saveRegistration(Registration registration);

	List<Registration> findAllRegistrations();

	void deleteRegistration(String ico, Date registrationDate);

}

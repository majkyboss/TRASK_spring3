package com.websystique.springmvc.service;

import java.util.List;

import org.joda.time.LocalDate;

import com.websystique.springmvc.model.Registration;

public interface RegistrationService {
	void saveRegistration(Registration registration);

	List<Registration> findAllRegistrations();

	void deleteRegistration(String ico, LocalDate registrationDate);

	boolean isRegistrationUnique(String ico, LocalDate reg_date);

	Registration findByKey(String ico, LocalDate regDate);

	List<Registration> findAllByManagerId(int managerId);

	void deleteRegistration(Registration registration);
}

package com.websystique.springmvc.dao;

import java.util.List;

import org.joda.time.LocalDate;

import com.websystique.springmvc.model.Registration;

public interface RegistrationDao {

	void saveRegistration(Registration registration);

	List<Registration> findAllRegistrations();

	void deleteRegistration(String ico, LocalDate registrationDate);

	Registration findByKey(String ico, LocalDate regDate);

	List<Registration> findAllByManagerId(int managerId);

}

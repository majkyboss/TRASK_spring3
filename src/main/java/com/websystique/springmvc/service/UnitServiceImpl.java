package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.AbstractDao;
import com.websystique.springmvc.model.Unit;

@Service("unitService")
@Transactional
public class UnitServiceImpl implements UnitService {

	@Autowired
	private AbstractDao dao;

	@Override
	public void saveUnit(Unit unit) {
		dao.persist(unit);
	}

}

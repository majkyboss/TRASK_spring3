package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UnitDao;
import com.websystique.springmvc.model.Unit;

@Service("unitService")
@Transactional
public class UnitServiceImpl implements UnitService {

	@Autowired
	@Qualifier("unitDao")
	private UnitDao dao;

	@Override
	public void saveUnit(Unit unit) {
		dao.saveUnit(unit);
	}

	@Override
	public Unit findById(int agentId, int branchId) {
		return dao.findById(agentId, branchId);
	}

	@Override
	public Unit findByUserId(int agentId) {
		return dao.findByUserId(agentId);
	}

	@Override
	public void deleteById(int userId, int branchId) {
		dao.deleteById(userId, branchId);
	}

}

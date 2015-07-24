package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Unit;

public interface UnitService {

	void saveUnit(Unit unit);

	Unit findById(int agentId, int branchId);

	Unit findByUserId(int id);

	void deleteById(int id, int id2);

}

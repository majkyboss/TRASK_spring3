package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.RegStatusDao;
import com.websystique.springmvc.model.RegStatus;

@Service("statusService")
@Transactional
public class RegStatusServiceImpl implements RegStatusService {

	@Autowired
	@Qualifier("regStatusDao")
	private RegStatusDao dao;

	@Override
	public List<RegStatus> findAllStatuses() {
		return dao.findAllStatuses();
	}

}

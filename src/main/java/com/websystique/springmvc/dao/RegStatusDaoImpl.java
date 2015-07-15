package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.RegStatus;

@Repository("regStatusDao")
public class RegStatusDaoImpl extends AbstractDao implements RegStatusDao {

	@Override
	public List<RegStatus> findAllStatuses() {
		Criteria criteria = getSession().createCriteria(RegStatus.class);
		return criteria.list();
	}

}

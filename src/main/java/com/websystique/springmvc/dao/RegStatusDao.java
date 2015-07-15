package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.RegStatus;

public interface RegStatusDao {

	List<RegStatus> findAllStatuses();

}

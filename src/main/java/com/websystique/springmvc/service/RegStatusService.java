package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.RegStatus;

public interface RegStatusService {

	List<RegStatus> findAllStatuses();

	RegStatus findById(int statusId);

}

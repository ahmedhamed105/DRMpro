package org.drm.services;

import java.util.Date;
import java.util.List;

import org.drm.dao.DRMDAO;
import org.drm.entity.Sobhy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("registerationServices")
@Transactional(propagation=Propagation.REQUIRED)

public class RegisterationServicesImpl implements RegisterationServices {

	@Autowired(required=true)
	private DRMDAO userDAO;
	
	public void addsobhy(Object sobhy) {
		
		userDAO.insert(sobhy);
	}

	public List<Sobhy> getsobhy(String p, Object value , Class entityClass) {
		// TODO Auto-generated method stub
		return userDAO.findByProperty(p, value,entityClass);
	}

}
	

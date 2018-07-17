package org.drm.services;

import java.util.List;
import org.drm.entity.Sobhy;

public interface RegisterationServices {
	public void addsobhy(Object sobhy);
	public List<Sobhy> getsobhy(String p , Object value , Class entityClass);

}

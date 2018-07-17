package org.drm.rest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.drm.entity.Sobhy;
import org.drm.services.RegisterationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Path("/services")
public class UserRestResource {

	
	@Autowired(required=true)
	@Qualifier("registerationServices")
	private RegisterationServices service;
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addsobhy/{userId}")
	public Sobhy getUser(@PathParam("userId") String name){
		Sobhy s = null ;
		try {
			Long x = 1l ; 
			List<Sobhy>list  = service.getsobhy("id", x,Sobhy.class);
			s = list.get(0);
			return s;
		}
		catch(Exception e)
		{
			System.err.println(e);
			Sobhy x = new Sobhy();
			x.setId(55);
			x.setName("ssss");
			return x ;
		}
		
		//return "sucessful" ;
	}

	
	
}

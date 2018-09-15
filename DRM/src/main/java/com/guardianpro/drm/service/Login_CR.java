package com.guardianpro.drm.service;

import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ahmed.elemam
 */

@Produces("application/json")
@XmlRootElement   
public class Login_CR {
    
  
 private String user;
 private String password;
 private String agentcode;
 private String application;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

 
  

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

  
 
 
 
 
 

    
}

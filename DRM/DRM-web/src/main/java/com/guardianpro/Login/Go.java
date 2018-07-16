/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.Login;

import Entities.HostInfo;
import Session.HostInfoFacadeLocal;
import javax.ejb.EJB;

/**
 *
 * @author ahmed.elemam
 */
public class Go {

    @EJB
    private HostInfoFacadeLocal hostInfoFacade;

    
    /**
     * Creates a new instance of Go
     */
    public Go() {
    }
    
    
   public void insert(){
       HostInfo info=new HostInfo();
      
      

     
     info.setHIp("ss");
     info.setHHost("ss");
     info.setHUser("ss");
     info.setHPort(30);
     
     hostInfoFacade.create(info);
    
    }
    
}

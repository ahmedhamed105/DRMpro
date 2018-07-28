/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.service;

import com.guardianpro.drm.entities.ApplicationUser;
import com.guardianpro.drm.entities.Applications;
import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginHistory;
import com.guardianpro.drm.entities.LoginPrev;
import com.guardianpro.drm.entities.LoginQuery;
import com.guardianpro.drm.entities.Terminal;
import com.guardianpro.drm.entities.User;
import com.guardianpro.drm.sessions.ApplicationUserFacade;
import com.guardianpro.drm.sessions.ApplicationsFacade;
import com.guardianpro.drm.sessions.DrmParameterFacade;
import com.guardianpro.drm.sessions.HostInfoFacade;
import com.guardianpro.drm.sessions.LoginHistoryFacade;
import com.guardianpro.drm.sessions.LoginPrevFacade;
import com.guardianpro.drm.sessions.LoginQueryFacade;
import com.guardianpro.drm.sessions.TerminalFacade;
import com.guardianpro.drm.sessions.TokeanGoFacade;
import com.guardianpro.drm.sessions.UserFacade;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author ahmedhamed
 */

@Stateless
@LocalBean
public class Login_check {
    
      @EJB
    private static LoginQueryFacade loginQueryFacade;

    @EJB
    private static ApplicationUserFacade applicationUserFacade;

    @EJB
    private static ApplicationsFacade applicationsFacade;

    @EJB
    private static TerminalFacade terminalFacade;

    @EJB
    private static TokeanGoFacade tokeanGoFacade;

    @EJB
    private static LoginHistoryFacade loginHistoryFacade;

    @EJB
    private static LoginPrevFacade loginPrevFacade;

    @EJB
    private static DrmParameterFacade drmParameterFacade;

    @EJB
    private static UserFacade userFacade;

    @EJB
    private static HostInfoFacade hostInfoFacade;
    
    
    
      /**
     * This is a sample web service operation
     */
  static String Serverkey = "SHARED_KEY";
  static String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

// 2048 bit keys should be secure until 2030 - https://web.archive.org/web/20170417095741/https://www.emc.com/emc-plus/rsa-labs/historical/twirl-and-rsa-key-size.htm
  static int SECURE_TOKEN_LENGTH = 256;
  static String Expire_time = "0";

 static SecureRandom random = new SecureRandom();

  static char[] symbols = CHARACTERS.toCharArray();


   static char[] buf = null;
    
    
    
    public static respons_login Login_check(String key,String  ip ,String  host,String  userx ,int  port ,String tokean,String application,String User,String Terminal) {
        respons_login resp=new respons_login();
        
         Login_ouput response = new Login_ouput();
   LoginPrev pre;
   long diff=0;
  
      DrmParameter para = drmParameterFacade.para_find("Server_key");
      if(para == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.HOST_notfound_key);
    response.setExpiretime("0");
    
    resp.setReponse(response);
    resp.setError(1);
    return resp;
      
      }
      
      
          //check Server key
      Serverkey=para.getParameterValue();
  
  if (Serverkey.equalsIgnoreCase(key)) {
      
      Date  date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
      
  //get ip is allowed or not

     HostInfo info1=hostInfoFacade.ip_find(ip);
     if(info1==null){
      
          response.setTokean("");
    response.setStatusCode(Error_codes.Host_IP_notfound);
    response.setExpiretime("0");
    
    resp.setReponse(response);
    resp.setError(1);
    return resp;
         
     }else{
    //check ip is allowed or not
    
          pre=loginPrevFacade.host_find(info1);        
         if(pre == null){
           response.setTokean("");
   response.setStatusCode(Error_codes.HOST_NO_prelogin);
    response.setExpiretime("0");  
          resp.setReponse(response);
       resp.setError(1);
    return resp;
         }
         
             if(pre.getAdminLock() == 1){
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Lock_Admin);
        response.setExpiretime("0");  
          resp.setReponse(response);
    resp.setError(1);
    return resp;
         }

         
        if(info1.getRequestcount() >= 3 && date.getTime() - info1.getUpdateDate().getTime() >= 1*60*1000 ){
           info1.setRequestcount(0);
           pre.setLockcount(pre.getLockcount()+1);
         }
        
            if(pre.getLockcount() >= 3){
              info1.setRequestcount(0);
              pre.setLockcount(0);
              pre.setAdminLock(1);
             }
        
     
      loginPrevFacade.edit(pre);
      
     
        
           if(pre.getAdminLock() == 1){
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Lock_Admin);
        response.setExpiretime("0");  
          resp.setReponse(response);
    resp.setError(1);
    return resp;
         }
         
         
        if(info1.getRequestcount() >= 3){
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Lock_3times);
        response.setExpiretime("0");  
          resp.setReponse(response);
    resp.setError(1);
    return resp;
         }
        
    

     
     
     }
           

 
// user Check
 
    User usr=userFacade.password_username(User);
        if(usr == null){
        
         response.setTokean("");
        response.setStatusCode(Error_codes.HOST_user_notfound);
        response.setExpiretime("0");  
          resp.setReponse(response);
    resp.setError(1);
    return resp;
        
        }            
           
     
            // user correct   
            
            // check application code is correct
                Applications appl=applicationsFacade.APP_find(application);
                   if(appl == null){
                       
                 // App not found NULL
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_APP_notfound);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_APP_notfound);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;
                   
                   }
                   
                   
             //check application user
             
                       ApplicationUser appuser=applicationUserFacade.App_user(appl, usr);
                         if(appuser == null){
                           // application not related to user
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_APP_notrelated_user);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_APP_notrelated_user);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;
                         
                         }
                         
                         
                         
                            
    LoginQuery query=loginQueryFacade.appuser_find(appuser);
    if(query == null){
            
      // LOGIN QUERU not found NULL
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Query_notfound);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Query_notfound);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;     
        
                   }
                         
                //qyery correct correct    
                
                          
                 
                 if(query.getErrorcount() > 3){
                     
                     
                        // lock 3 times
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Lock_query_3times);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Lock_query_3times);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;  
               
                 }
                 
                if(query.getUserlock() > 3){
                    
                                
                        // lock 3 times
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Lock_query_Admin);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Lock_query_Admin);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;  
                 
                 }

            
                
                
                       
            //check terminal id
               Terminal terminal_id = terminalFacade.Terminal_find(Terminal);
               if(terminal_id == null){
                 // terminal not found NULL
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_terminal_notfound);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
                 query.setErrorcount(query.getErrorcount()+1);
                 
                 if(query.getErrorcount() > 3){
                 query.setUserlock(query.getUserlock()+1);
                 query.setErrorcount(0);
                 }
                 
                if(query.getUserlock() > 3){
                  query.setUseradmin(query.getUseradmin()+1);
                   query.setErrorcount(0);
                 }

                loginQueryFacade.edit(query);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_terminal_notfound);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;
               }
               
               
               //terminal correct
               

    
    
                   //user login before 1-check login or not then check timeexpire
   Expire_time=query.getExpiretime().trim();
                   Calendar previous = Calendar.getInstance();
previous.setTime(query.getUpdateDate());
Calendar now = Calendar.getInstance();
 diff = now.getTimeInMillis() - previous.getTimeInMillis();
if(diff >= Integer.parseInt(Expire_time) * 60 * 1000)
{
    //after  expire minutes difference
    
       // HOST_Expired 
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Expired);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Expired);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;     
        
}else{
 //before  expire minutes difference

   if(query.getLogin() == 1){      
       

    if(query.getTokean().trim().equals(tokean.trim())){
        
        // Tokean not correct 
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Sucess);
                loginHistoryFacade.create(history);
                
        response.setTokean(tokean);
        response.setStatusCode(Error_codes.HOST_Sucess);
        response.setExpiretime(String.valueOf(Integer.parseInt(Expire_time)-(int)((diff/60)/1000)));  
        resp.setError(0);
        return resp; 
        
    }else{
        
           // Tokean not correct 
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Tokean_Wrong);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Tokean_Wrong);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;  
    
    
    }
         
                    
    
  
    }else{
           // Not login
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.HOST_Want_Login);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.HOST_Want_Login);
        response.setExpiretime("0");  
        resp.setError(1);
        return resp;  
   
   
   }


}
                   
                
                   
                   
                   
               
             

  } else {
   response.setTokean("");
   response.setStatusCode(Error_codes.HOST_Wrong_key);
    response.setExpiretime("0");
            resp.setError(1);
        return resp;  
  }
        
    }
    
}

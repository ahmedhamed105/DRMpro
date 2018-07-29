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
import com.guardianpro.drm.entities.TrxFields;
import com.guardianpro.drm.entities.TrxType;
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
import com.guardianpro.drm.sessions.TrxFieldsFacade;
import com.guardianpro.drm.sessions.TrxTypeFacade;
import com.guardianpro.drm.sessions.UserFacade;
import java.sql.Date;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ahmedhamed
 */

@Path("/payment")
@Stateless
@LocalBean
public class TransactionController {

    @EJB
    private TrxFieldsFacade trxFieldsFacade;

    @EJB
    private TrxTypeFacade trxTypeFacade;
    
    
     @EJB
    private LoginQueryFacade loginQueryFacade;

    @EJB
    private ApplicationUserFacade applicationUserFacade;

    @EJB
    private ApplicationsFacade applicationsFacade;

    @EJB
    private TerminalFacade terminalFacade;

    @EJB
    private TokeanGoFacade tokeanGoFacade;

    @EJB
    private LoginHistoryFacade loginHistoryFacade;

    @EJB
    private LoginPrevFacade loginPrevFacade;

    @EJB
    private DrmParameterFacade drmParameterFacade;

    @EJB
    private UserFacade userFacade;

    @EJB
    private HostInfoFacade hostInfoFacade;
    
    
    
    
    
    
    
    
    
    String Expire_time = "10";
    String Serverkey = "SHARED_KEY";
    
    
    
    
 @POST
 @Path("/{key}") 
 @Produces(MediaType.APPLICATION_JSON) 
 @Consumes(MediaType.APPLICATION_JSON) 
 public Login_ouput Check(@Context HttpServletRequest req,@PathParam("key") String key,Trx_request trx) {
     
    String  ip = req.getRemoteAddr();
    String  host = req.getRemoteHost();
    String  userx = req.getRemoteUser();
    int  port = req.getRemotePort();
     
     
 String user= trx.getLogin().getUser().trim();
 String tokean= trx.getLogin().getTokean().trim();
 String tid= trx.getLogin().getAgentcode().trim();
 String app= trx.getLogin().getApplication().trim();
 
  respons_login res=  Login_check(key, ip, host, userx, port, tokean, app, user, tid);
   
if(res.getError() == 1){
 return  res.getReponse();
}else{
     Login_ouput response = new Login_ouput();
     
    TrxType ttype=trxTypeFacade.find(trx.getType());
    if(ttype == null){
     response.setTokean("");
    response.setStatusCode(Error_codes.TRX_type_error);
    response.setExpiretime("0");
    return response;
    }
    
    Terminal term=res.getTerm();
    if(term == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.Terminal_error);
    response.setExpiretime("0");
    return response;
    }
    
    Field_value [] fields=trx.getFields();
    
    if(fields == null || fields.length == 0){
    response.setTokean("");
    response.setStatusCode(Error_codes.No_field);
    response.setExpiretime("0");
    return response;
    }
    
    for(int i=0;i<fields.length;i++){
        
        TrxFields field =  trxFieldsFacade.trxtype_find(fields[i].getKey());
        if(field == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.field_not_found);
    response.setExpiretime("0");
    return response;
    }
    
    
    }
    
    


}
  
   return  res.getReponse();  
 }
 
 
   public  respons_login Login_check(String key,String  ip ,String  host,String  userx ,int  port ,String tokean,String application,String User,String Terminal) {
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
         HostInfo info=new HostInfo();       
         info.setHIp(ip);
     info.setHHost(host);
     info.setHUser(userx);
     info.setHPort(port);
     info.setRequestcount(1);  
     info.setCreateDate(date);
     info.setUpdateDate(date);
     
  hostInfoFacade.create(info);
  
  //create a login_prev
  
         
  info1=info;    
  
         
     }
     
     
    //check ip is allowed or not
    
          pre=loginPrevFacade.host_find(info1);        
         if(pre == null){
            pre=new LoginPrev();
         pre.setAdminLock(0);
         pre.setHostinfoID(info1);
         pre.setCreateDate(date);
         pre.setUpdateDate(date);
         pre.setSerKey(key);
         pre.setLockcount(0);
        loginPrevFacade.create(pre);
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
        resp.setReponse(response);
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
        resp.setReponse(response);
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
        resp.setReponse(response);
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
        resp.setReponse(response);
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
        resp.setReponse(response);
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
        resp.setReponse(response);
        resp.setError(1);
        return resp;     
        
}else{
 //before  expire minutes difference

   if(query.getLogin() == 1){      
       

    if(query.getTokean().trim().equals(tokean.trim())){
        
             System.out.println("com.guardianpro.drm.service.PaymentController.Login_check() 34");
               
        
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
        resp.setReponse(response);
        resp.setError(0);
        resp.setTerm(terminal_id);
        return resp; 
        
    }else{
        
        
             System.out.println("com.guardianpro.drm.service.PaymentController.Login_check() 24");
               
        
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
        resp.setReponse(response);
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
        resp.setReponse(response);
        resp.setError(1);
        return resp;  
   
   
   }


}
                   
                
                   
                   
                   
               
             

  } else {
   response.setTokean("");
   response.setStatusCode(Error_codes.HOST_Wrong_key);
    response.setExpiretime("0");
    resp.setReponse(response);
            resp.setError(1);
        return resp;  
  }
        
    }
   

    
}

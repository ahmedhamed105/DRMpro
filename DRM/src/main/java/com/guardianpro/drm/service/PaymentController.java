package com.guardianpro.drm.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import com.guardianpro.drm.entities.ApplicationUser;
import com.guardianpro.drm.entities.Applications;
import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginHistory;
import com.guardianpro.drm.entities.LoginPrev;
import com.guardianpro.drm.entities.LoginQuery;
import com.guardianpro.drm.entities.Terminal;
import com.guardianpro.drm.entities.TokeanGo;
import com.guardianpro.drm.entities.User;
import static com.guardianpro.drm.service.Login_check.Serverkey;
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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author ahmed.elemam
 */


@Path("/Login")
@Stateless
@LocalBean
public class PaymentController {

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
    
    
    
    
    
    
    
    


    

    /**
     * This is a sample web service operation
     */
  String Serverkey = "SHARED_KEY";
  String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

// 2048 bit keys should be secure until 2030 - https://web.archive.org/web/20170417095741/https://www.emc.com/emc-plus/rsa-labs/historical/twirl-and-rsa-key-size.htm
  int SECURE_TOKEN_LENGTH = 256;
  String Expire_time = "10";

  SecureRandom random = new SecureRandom();

     char[] symbols = CHARACTERS.toCharArray();


   char[] buf = null;



        @GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}

  @POST
 @Path("/Auth/{key}") 
 @Produces(MediaType.APPLICATION_JSON) 
 @Consumes(MediaType.APPLICATION_JSON) 
 public Login_ouput Login(@Context HttpServletRequest req,@PathParam("key") String key,Login_CR Ilogin) {

  Login_ouput response = new Login_ouput();
   LoginPrev pre;
   long diff=0;
  
      DrmParameter para = drmParameterFacade.para_find("Server_key");
       DrmParameter para1 = drmParameterFacade.para_find("Tokean_length");
        DrmParameter para2 = drmParameterFacade.para_find("Tokean_expire");
      if(para == null || para1 == null || para2 == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.notfound_key);
    response.setExpiretime("0");
      return response;
      }else{
          //check Server key
      Serverkey=para.getParameterValue();
      SECURE_TOKEN_LENGTH=Integer.parseInt(para1.getParameterValue());
       buf = new char[SECURE_TOKEN_LENGTH];
       Expire_time=para2.getParameterValue();
  
  if (Serverkey.equalsIgnoreCase(key)) {
      
      Date  date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
      
  //get ip is allowed or not
      
     String  ip = req.getRemoteAddr();
     String  host = req.getRemoteHost();
     String  userx = req.getRemoteUser();
     int  port = req.getRemotePort();
     
     
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
  
          pre=new LoginPrev();
         pre.setAdminLock(0);
         pre.setHostinfoID(info);
         pre.setCreateDate(date);
         pre.setUpdateDate(date);
         pre.setSerKey(key);
         pre.setLockcount(0);
        loginPrevFacade.create(pre);
      
  info1=info;
     }else{
         
    

  
    //check ip is allowed or not
    
          pre=loginPrevFacade.host_find(info1);        
         if(pre == null){
           response.setTokean("");
   response.setStatusCode(Error_codes.NO_prelogin);
    response.setExpiretime("0");  
         return response;
         }
         
             if(pre.getAdminLock() == 1){
        response.setTokean("");
        response.setStatusCode(Error_codes.Lock_Admin);
        response.setExpiretime("0");  
         return response;
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
        response.setStatusCode(Error_codes.Lock_Admin);
        response.setExpiretime("0");  
         return response;
         }
         
         
        if(info1.getRequestcount() >= 3){
        response.setTokean("");
        response.setStatusCode(Error_codes.Lock_3times);
        response.setExpiretime("0");  
         return response;
         }
        
    

     
     
     }
           

     
 
   // System.out.println("ip :"+ip+" "+host+" "+userx+" "+port);
   
     // Process the request
   // ....
    
 String user= Ilogin.getUser().trim();
 String password= Ilogin.getPassword().trim();
 String tid= Ilogin.getAgentcode().trim();
 String app= Ilogin.getApplication().trim();
 
 
    
        if(userFacade.user_find(user)){
            
            User usr=userFacade.password_username(user);
            if(usr != null){
            String decrypted = Encryption.decrypt(usr.getUserPasswordID().getPassword());
                System.out.println(decrypted);
            if(decrypted.equals(password)){
            // user correct   
            
            // check application code is correct
                Applications appl=applicationsFacade.APP_find(app);
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
                history.setErrorCode(Error_codes.APP_notfound);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.APP_notfound);
        response.setExpiretime("0");  
         return response;
                   
                   }else{
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
                history.setErrorCode(Error_codes.APP_notrelated_user);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.APP_notrelated_user);
        response.setExpiretime("0");  
         return response;
                         
                         }else{
                             
                             
                String toke;
                TokeanGo tok = null;
                LoginQuery query=loginQueryFacade.appuser_find(appuser);
                       
            //check terminal id
               Terminal terminal_id = terminalFacade.Terminal_find(tid);
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
                history.setErrorCode(Error_codes.terminal_notfound);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
                if(query!=null){
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
                }
                
        response.setTokean("");
        response.setStatusCode(Error_codes.terminal_notfound);
        response.setExpiretime("0");  
         return response;
               }
               
               
               
               
             
                   if(query == null){
             // first login               
     info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(0);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);
  
   toke=nextToken();
              tok  =new TokeanGo();
                tok.setCreateDate(date);
                tok.setUpdateDate(date);
                tok.setLoginprevID(pre);
                tok.setUserID(usr);
                tok.setTokean(toke);
                tok.setExipretime(Expire_time);
                tok.setTerminalID(terminal_id);
                tokeanGoFacade.create(tok);
                
                
                
                    query=new LoginQuery();
                   query.setCreateDate(date);
                   query.setUpdateDate(date);
                   query.setLogin(1);
                   query.setTokean(toke);
                   query.setApplicationuserID(appuser);
                   query.setExpiretime(Expire_time);  
                   query.setErrorcount(0);
                   query.setUseradmin(0);
                   query.setUserlock(0);
                   loginQueryFacade.create(query);
                   
                   
                   
                        LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginsucess(date);
                history.setFailedSucess(1);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.Sucess_login);
                loginHistoryFacade.create(history);
         
                   
                   }
                   
                   
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
                history.setErrorCode(Error_codes.Lock_query_3times);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.Lock_query_3times);
        response.setExpiretime("0");  
        return response;  
               
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
                history.setErrorCode(Error_codes.Lock_query_Admin);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.Lock_query_Admin);
        response.setExpiretime("0");  
        return response;  
                 
                 }

                   
                   
                   
                   
                   
                   
                   //user login before 1-check login or not then check timeexpire
                   
                   Calendar previous = Calendar.getInstance();
previous.setTime(query.getUpdateDate());
Calendar now = Calendar.getInstance();
 diff = now.getTimeInMillis() - previous.getTimeInMillis();
if(diff >= Integer.parseInt(Expire_time) * 60 * 1000)
{
    //after  expire minutes difference
    
      toke=nextToken();
          
       query.setTokean(toke);
       query.setLogin(1);
       query.setUpdateDate(date);
       loginQueryFacade.edit(query);
       
        tok=tokeanGoFacade.preterm_find(pre, terminal_id);
        if(!tok.getExipretime().equals(Expire_time)){
        tok.setExipretime(Expire_time);
        }
        tok.setTokean(toke);
        tokeanGoFacade.edit(tok);
   
        
        info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(0);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);  
  
             LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginsucess(date);
                history.setFailedSucess(1);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.Sucess_login);
                loginHistoryFacade.create(history);
}else{
 //before  expire minutes difference

   if(query.getLogin() == 1){      
       
       
       tok=tokeanGoFacade.preterm_find(pre, terminal_id);
       
       
         if(!tok.getExipretime().equals(Expire_time)){
        tok.setExipretime(Expire_time);
        tokeanGoFacade.edit(tok);
        }
         
                    
     info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(0);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);  
  
             LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginsucess(date);
                history.setFailedSucess(1);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.Sucess_login);
                loginHistoryFacade.create(history);
  
    }else{
          toke=nextToken();
          
         query.setTokean(toke);
       query.setLogin(1);
       query.setUpdateDate(date);
       loginQueryFacade.edit(query);
       
         tok=tokeanGoFacade.preterm_find(pre, terminal_id);
         tok.setTokean(toke);
           if(!tok.getExipretime().equals(Expire_time)){
        tok.setExipretime(Expire_time);
        }
         tokeanGoFacade.edit(tok);
          
   
        info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(0);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);  
  
             LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginsucess(date);
                history.setFailedSucess(1);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.Sucess_login);
                loginHistoryFacade.create(history);
   
   
   }


}
                   
                
                   
                   
                   
               
          
  
     response.setTokean(tok.getTokean());
        response.setStatusCode(Error_codes.Sucess_login);
        response.setExpiretime(String.valueOf(Integer.parseInt(Expire_time)-(int)((diff/60)/1000)));  
         return response;
               
               
               
                   }
            
                   }
           
            
            }else{
                
 // user password wrong 
     info1.setRequestcount(info1.getRequestcount()+1);   
     hostInfoFacade.edit(info1);
     
        LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                  history.setLoginprevID(pre);
                          history.setErrorCode(Error_codes.Wrong_password);
                loginHistoryFacade.create(history);
            
                response.setTokean("");
        response.setStatusCode(Error_codes.Wrong_password);
        response.setExpiretime("0");  
         return response;
            }
            

     
        
        
        }else{
             // user not found NULL
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.User_notfound);
                loginHistoryFacade.create(history);
                
                   info1.setRequestcount(info1.getRequestcount()+1);   
     hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.User_notfound);
        response.setExpiretime("0");  
         return response;
        
        
        }
        }else{
           // user not found 
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.User_notfound);
                loginHistoryFacade.create(history);
                
                   info1.setRequestcount(info1.getRequestcount()+1);   
     hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.User_notfound);
        response.setExpiretime("0");  
         return response;
        
        }
 

  } else {
   response.setTokean("");
   response.setStatusCode(Error_codes.Wrong_key);
    response.setExpiretime("0");
  }
  
      }
  
     // Return success response to the client.
  return response;
 }
 


  @POST
 @Path("/Logout/{key}") 
 @Produces(MediaType.APPLICATION_JSON) 
 @Consumes(MediaType.APPLICATION_JSON) 
  public Login_ouput Logout(@Context HttpServletRequest req,@PathParam("key") String key,Login_CR Ilogin) {

  Login_ouput response = new Login_ouput();
   LoginPrev pre;
   long diff=0;
  
      DrmParameter para = drmParameterFacade.para_find("Server_key");
       DrmParameter para1 = drmParameterFacade.para_find("Tokean_length");
        DrmParameter para2 = drmParameterFacade.para_find("Tokean_expire");
      if(para == null || para1 == null || para2 == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.notfound_key_out);
    response.setExpiretime("0");
      
      }else{
          //check Server key
      Serverkey=para.getParameterValue();
      SECURE_TOKEN_LENGTH=Integer.parseInt(para1.getParameterValue());
       buf = new char[SECURE_TOKEN_LENGTH];
       Expire_time=para2.getParameterValue();
  
  if (Serverkey.equalsIgnoreCase(key)) {
      
      Date  date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
      
  //get ip is allowed or not
      
     String  ip = req.getRemoteAddr();
     String  host = req.getRemoteHost();
     String  userx = req.getRemoteUser();
     int  port = req.getRemotePort();
     
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
  
          pre=new LoginPrev();
         pre.setAdminLock(0);
         pre.setHostinfoID(info);
         pre.setCreateDate(date);
         pre.setUpdateDate(date);
         pre.setSerKey(key);
         pre.setLockcount(0);
        loginPrevFacade.create(pre);
      
  info1=info;
     }else{
         
    

  
    //check ip is allowed or not
    
          pre=loginPrevFacade.host_find(info1);        
         if(pre == null){
           response.setTokean("");
   response.setStatusCode(Error_codes.NO_prelogin_out);
    response.setExpiretime("0");  
         return response;
         }
         
             if(pre.getAdminLock() == 1){
        response.setTokean("");
        response.setStatusCode(Error_codes.Lock_Admin_out);
        response.setExpiretime("0");  
         return response;
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
        response.setStatusCode(Error_codes.Lock_Admin_out);
        response.setExpiretime("0");  
         return response;
         }
         
         
        if(info1.getRequestcount() >= 3){
        response.setTokean("");
        response.setStatusCode(Error_codes.Lock_3times_out);
        response.setExpiretime("0");  
         return response;
         }
        
    

     
     
     }
           

     
 
   // System.out.println("ip :"+ip+" "+host+" "+userx+" "+port);
   
     // Process the request
   // ....
    
 String user= Ilogin.getUser().trim();
 String password= Ilogin.getPassword().trim();
 String tid= Ilogin.getAgentcode().trim();
 String app= Ilogin.getApplication().trim();
 
 
    
        if(userFacade.user_find(user)){
            
            User usr=userFacade.password_username(user);
            if(usr != null){
            String decrypted = Encryption.decrypt(usr.getUserPasswordID().getPassword());
                System.out.println(decrypted);
            if(decrypted.equals(password)){
            // user correct   
            
            // check application code is correct
                Applications appl=applicationsFacade.APP_find(app);
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
                history.setErrorCode(Error_codes.APP_notfound_out);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.APP_notfound_out);
        response.setExpiretime("0");  
         return response;
                   
                   }else{
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
                history.setErrorCode(Error_codes.APP_notrelated_user_out);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.APP_notrelated_user_out);
        response.setExpiretime("0");  
         return response;
                         
                         }else{
                       
            //check terminal id
               Terminal terminal_id = terminalFacade.Terminal_find(tid);
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
                history.setErrorCode(Error_codes.terminal_notfound_out);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.terminal_notfound_out);
        response.setExpiretime("0");  
         return response;
               }else{
                    String toke;
                    TokeanGo tok = null;
                 LoginQuery query=loginQueryFacade.appuser_find(appuser);
                   if(query == null){
             // Error user not login  
                       
                   
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.user_notlogin_out);
                loginHistoryFacade.create(history);
                
                info1.setRequestcount(info1.getRequestcount()+1);   
                hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.user_notlogin_out);
        response.setExpiretime("0");  
         return response;
                   
                   }else{
                   //user login out
      
   if(query.getLogin() == 1){      
       
       query.setLogin(0);
       query.setUpdateDate(date);
       loginQueryFacade.edit(query);
       tok=tokeanGoFacade.preterm_find(pre, terminal_id);
       
         if(!tok.getExipretime().equals(Expire_time)){
        tok.setExipretime(Expire_time);
      
        }
         
           tokeanGoFacade.edit(tok);
         
                    
     info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(0);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);  
  
             LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginsucess(date);
                history.setFailedSucess(1);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.Sucess_login_out);
                loginHistoryFacade.create(history);
  
    }else{
        
       // login logout before 
          query.setLogin(0);
       loginQueryFacade.edit(query);
       
       
         tok=tokeanGoFacade.preterm_find(pre, terminal_id);
           if(!tok.getExipretime().equals(Expire_time)){
        tok.setExipretime(Expire_time);
        }
         tokeanGoFacade.edit(tok);

   


}
                   
                
                   
                   
                   }
               
          
  
     response.setTokean(tok.getTokean());
        response.setStatusCode(Error_codes.Sucess_login_out);
        response.setExpiretime("0");  
         return response;
               
               }
               
                   }
            
                   }
           
            
            }else{
                
 // user password wrong 
     info1.setRequestcount(info1.getRequestcount()+1);   
     hostInfoFacade.edit(info1);
     
        LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                  history.setLoginprevID(pre);
                          history.setErrorCode(Error_codes.Wrong_password_out);
                loginHistoryFacade.create(history);
            
                response.setTokean("");
        response.setStatusCode(Error_codes.Wrong_password_out);
        response.setExpiretime("0");  
         return response;
            }
            

     
        
        
        }else{
             // user not found NULL
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.User_notfound_out);
                loginHistoryFacade.create(history);
                
                   info1.setRequestcount(info1.getRequestcount()+1);   
     hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.User_notfound_out);
        response.setExpiretime("0");  
         return response;
        
        
        }
        }else{
           // user not found 
                LoginHistory history=new LoginHistory();
                history.setHIp(ip);
                history.setHHost(host);
                history.setHUser(userx);
                history.setHPort(port);
                history.setLoginfailed(date);
                history.setFailedSucess(0);
                history.setLoginprevID(pre);
                history.setErrorCode(Error_codes.User_notfound_out);
                loginHistoryFacade.create(history);
                
                   info1.setRequestcount(info1.getRequestcount()+1);   
     hostInfoFacade.edit(info1);
                
        response.setTokean("");
        response.setStatusCode(Error_codes.User_notfound_out);
        response.setExpiretime("0");  
         return response;
        
        }
 

  } else {
   response.setTokean("");
   response.setStatusCode(Error_codes.Wrong_key_out);
    response.setExpiretime("0");
  }
  
      }
  
     // Return success response to the client.
  return response;
 }
  
  
    @POST
 @Path("/Check/{key}") 
 @Produces(MediaType.APPLICATION_JSON) 
 @Consumes(MediaType.APPLICATION_JSON) 
 public Login_ouput Check(@Context HttpServletRequest req,@PathParam("key") String key,Check_Tokean Ilogin) {
     
    String  ip = req.getRemoteAddr();
    String  host = req.getRemoteHost();
    String  userx = req.getRemoteUser();
    int  port = req.getRemotePort();
     
     
 String user= Ilogin.getUser().trim();
 String tokean= Ilogin.getTokean().trim();
 String tid= Ilogin.getAgentcode().trim();
 String app= Ilogin.getApplication().trim();
 
   respons_login res=  Login_check(key, ip, host, userx, port, tokean, app, user, tid);
   
        System.out.println("com.guardianpro.drm.service.PaymentController.Login_check() "+res.getReponse().getStatusCode());
               
        
   
   return  res.getReponse();
     
 }

 
 public  String nextToken() {
    for (int idx = 0; idx < buf.length; ++idx)
        buf[idx] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
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

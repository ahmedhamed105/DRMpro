package com.guardianpro.drm.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginHistory;
import com.guardianpro.drm.entities.LoginPrev;
import com.guardianpro.drm.entities.TokeanGo;
import com.guardianpro.drm.entities.User;
import com.guardianpro.drm.sessions.DrmParameterFacade;
import com.guardianpro.drm.sessions.HostInfoFacade;
import com.guardianpro.drm.sessions.LoginHistoryFacade;
import com.guardianpro.drm.sessions.LoginPrevFacade;
import com.guardianpro.drm.sessions.TokeanGoFacade;
import com.guardianpro.drm.sessions.UserFacade;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


@Path("/payment")
@Stateless
@LocalBean
public class PaymentController {

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
 private String Serverkey = "SHARED_KEY";
 private static  String SUCCESS_STATUS = "success";
 private static  String ERROR_STATUS = "error";
 private static  int CODE_SUCCESS = 100;
 private static  int AUTH_FAILURE = 102;
 
 
 public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

// 2048 bit keys should be secure until 2030 - https://web.archive.org/web/20170417095741/https://www.emc.com/emc-plus/rsa-labs/historical/twirl-and-rsa-key-size.htm
  int SECURE_TOKEN_LENGTH = 256;
  String Expire_time = "10";

 final SecureRandom random = new SecureRandom();

 static final char[] symbols = CHARACTERS.toCharArray();
    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

   char[] buf = null;



        @GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}

  @POST
 @Path("/Login/{key}") 
 @Produces(MediaType.APPLICATION_JSON) 
 @Consumes(MediaType.APPLICATION_JSON) 
 public Login_ouput Login(@Context HttpServletRequest req,@PathParam("key") String key,Login_CR Ilogin) {

  Login_ouput response = new Login_ouput();
   LoginPrev pre;
  
      DrmParameter para = drmParameterFacade.para_find("Server_key");
       DrmParameter para1 = drmParameterFacade.para_find("Tokean_length");
        DrmParameter para2 = drmParameterFacade.para_find("Tokean_expire");
      if(para == null || para1 == null || para2 == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.notfound_key);
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
    
 String user= Ilogin.getUser();
 String password= Ilogin.getPassword();
 String tid= Ilogin.getAgentcode();
 String app= Ilogin.getApplication();
 
 
    
        if(userFacade.user_find(user)){
            
            User usr=userFacade.password_username(user);
            if(usr != null && usr.getUserPasswordID().getPassword().equals(password)){
            // user correct    
            
           
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
         
     info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(0);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);
                TokeanGo tok=new TokeanGo();
                tok.setCreateDate(date);
                tok.setUpdateDate(date);
                tok.setLoginprevID(pre);
                tok.setUserID(usr);
                tok.setTokean(nextToken());
                tokeanGoFacade.create(tok);
  
     response.setTokean(tok.getTokean());
        response.setStatusCode(Error_codes.Sucess_login);
        response.setExpiretime(Expire_time);  
         return response;
            
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
 
 
 public  String nextToken() {
    for (int idx = 0; idx < buf.length; ++idx)
        buf[idx] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
}

   

  
}

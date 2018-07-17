package com.guardianpro.drm.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginPrev;
import com.guardianpro.drm.sessions.DrmParameterFacade;
import com.guardianpro.drm.sessions.HostInfoFacade;
import com.guardianpro.drm.sessions.LoginPrevFacade;
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


@Path("/payment")
@Stateless
@LocalBean
public class PaymentController {

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
public static final int SECURE_TOKEN_LENGTH = 256;

private static final SecureRandom random = new SecureRandom();

private static final char[] symbols = CHARACTERS.toCharArray();

private static final char[] buf = new char[SECURE_TOKEN_LENGTH];



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
  
      DrmParameter para = drmParameterFacade.para_find("Server_key");
      if(para == null){
    response.setTokean("");
    response.setStatusCode(Error_codes.notfound_key);
    response.setExpiretime("0");
      
      }else{
          //check Server key
      Serverkey=para.getParameterValue();
  
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
  
         LoginPrev pre=new LoginPrev();
         pre.setAdminLock(0);
         pre.setHostinfoID(info);
         pre.setCreateDate(date);
         pre.setUpdateDate(date);
         pre.setSerKey(key);
         pre.setLockcount(0);
        loginPrevFacade.create(pre);
      
  info1=info;
     }else{
     info1.setHHost(host);
     info1.setHUser(userx);
     info1.setHPort(port);
     info1.setRequestcount(info1.getRequestcount()+1);  
     info1.setUpdateDate(date);
     
  hostInfoFacade.edit(info1);
  
    //check ip is allowed or not
     
     }
           

     
 
   // System.out.println("ip :"+ip+" "+host+" "+userx+" "+port);
    
  String user= Ilogin.getUser();
 String password= Ilogin.getPassword();
 String tid= Ilogin.getAgentcode();
 String app= Ilogin.getApplication();
  
   // Process the request
   // ....

    System.out.println("ip :"+user+" "+password+" "+tid+" "+app+" "+key);
   
   
   response.setTokean(nextToken());
   response.setStatusCode(CODE_SUCCESS);
   response.setExpiretime("10");
  } else {
   response.setTokean("");
   response.setStatusCode(Error_codes.Wrong_key);
    response.setExpiretime("0");
  }
  
      }
  
     // Return success response to the client.
  return response;
 }
 
 
 public static String nextToken() {
    for (int idx = 0; idx < buf.length; ++idx)
        buf[idx] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
}

  
}

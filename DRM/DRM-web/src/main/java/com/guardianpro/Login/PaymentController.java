/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.Login;


import Entities.HostInfo;
import Session.HostInfoFacadeLocal;
import java.security.SecureRandom;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author ahmed.elemam
 */


@Path("/payment")
public class PaymentController {


    

    /**
     * This is a sample web service operation
     */
   private final String sharedKey = "SHARED_KEY";
 private static final String SUCCESS_STATUS = "success";
 private static final String ERROR_STATUS = "error";
 private static final int CODE_SUCCESS = 100;
 private static final int AUTH_FAILURE = 102;
 
 
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
  if (sharedKey.equalsIgnoreCase(key)) {
      
      HostInfo info=new HostInfo();
      
      
      
     String  ip = req.getRemoteAddr();
     String  host = req.getRemoteHost();
     String  userx = req.getRemoteUser();
     int  port = req.getRemotePort();
     
     info.setHIp("ss");
     info.setHHost("ss");
     info.setHUser("ss");
     info.setHPort(30);
     
 //  hostInfoFacade.create(info);
     
  //hostInfoFacade.create(info);
       
    System.out.println("ip :"+ip+" "+host+" "+userx+" "+port);
    
  String user= Ilogin.getUser();
 String password= Ilogin.getPassword();
 String tid= Ilogin.getAgentcode();
 String app= Ilogin.getApplication();
  
   // Process the request
   // ....

    System.out.println("ip :"+user+" "+password+" "+tid+" "+app);
   
   
   response.setTokean(nextToken());
   response.setStatusCode(CODE_SUCCESS);
   response.setExpiretime("10");
  } else {
   response.setTokean(ERROR_STATUS);
   response.setStatusCode(AUTH_FAILURE);
    response.setExpiretime("0");
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

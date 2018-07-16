/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;


import java.security.SecureRandom;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahmed.elemam
 */

@RestController
@RequestMapping("/payment")
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
 
 
 
 @RequestMapping(value = "/Login", method = RequestMethod.POST)
 public Login_ouput pay(@RequestParam(value = "key") String key, @RequestBody Login_CR request) {
  Login_ouput response = new Login_ouput();
  if (sharedKey.equalsIgnoreCase(key)) {
   String user= request.getUser();
  String password= request.getPassword();
  String tid= request.getTid();
  String mid= request.getMid();
  String keys = request.getKey();
   // Process the request
   // ....


   
   
   response.setTokean(nextToken());
   response.setCode(CODE_SUCCESS);
  } else {
   response.setTokean(ERROR_STATUS);
   response.setCode(AUTH_FAILURE);
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

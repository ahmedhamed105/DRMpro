/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;


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
   // Return success response to the client.
   response.setTokean(SUCCESS_STATUS);
   response.setCode(CODE_SUCCESS);
  } else {
   response.setTokean(ERROR_STATUS);
   response.setCode(AUTH_FAILURE);
  }
  return response;
 }
}

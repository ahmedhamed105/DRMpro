/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.Login;

/**
 *
 * @author ahmed.elemam
 */
public class Login_ouput {
 
    
 private Integer StatusCode;
 private String tokean;
  private String Expiretime;

    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getExpiretime() {
        return Expiretime;
    }

    public void setExpiretime(String Expiretime) {
        this.Expiretime = Expiretime;
    }

    

    public String getTokean() {
        return tokean;
    }

    public void setTokean(String tokean) {
        this.tokean = tokean;
    }
 
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.service;

/**
 *
 * @author ahmed.elemam
 */
public class Error_codes {
    
    //login
    static int Sucess_login=101;
    static int notfound_key=101;
    static int Wrong_key=102;
    static int Lock_3times=103;
    static int Lock_Admin=104;
    static int NO_prelogin=105;
    static int User_notfound=106;
    static int Wrong_password=107;
    static int terminal_notfound=108;
    static int APP_notfound=109;
    static int APP_notrelated_user=110;
    
    
    //logout
    static int Sucess_login_out=201;
    static int notfound_key_out=201;
    static int Wrong_key_out=202;
    static int Lock_3times_out=203;
    static int Lock_Admin_out=204;
    static int NO_prelogin_out=205;
    static int User_notfound_out=206;
    static int Wrong_password_out=207;
    static int terminal_notfound_out=208;
    static int APP_notfound_out=209;
    static int APP_notrelated_user_out=210;
    static int user_notlogin_out=211;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.service;

import com.guardianpro.drm.entities.Terminal;

/**
 *
 * @author ahmedhamed
 */
public class respons_login {
    
    
    Login_ouput reponse;
    int error;
    Terminal term;

    public Login_ouput getReponse() {
        return reponse;
    }

    public void setReponse(Login_ouput reponse) {
        this.reponse = reponse;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Terminal getTerm() {
        return term;
    }

    public void setTerm(Terminal term) {
        this.term = term;
    }
    
    

     
}

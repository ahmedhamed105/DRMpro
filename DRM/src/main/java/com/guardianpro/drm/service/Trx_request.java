/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.service;

/**
 *
 * @author ahmedhamed
 */
public class Trx_request {
Check_Tokean login;
int type;    
Field_value [] fields;

    public Check_Tokean getLogin() {
        return login;
    }

    public void setLogin(Check_Tokean login) {
        this.login = login;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

   

    public Field_value[] getFields() {
        return fields;
    }

    public void setFields(Field_value[] fields) {
        this.fields = fields;
    }






    
    
}

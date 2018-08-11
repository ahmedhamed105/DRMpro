/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.converter;

import com.drm.facade.services.ReportService;
import com.drm.managedbeans.AbstractManagedBean;
import com.drm.model.entities.Reports;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author ahmedhamed
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author ahmed.elemam
 */
@Named("Creport")
@ApplicationScoped
public class Creport implements Converter{

    @EJB
    private ReportService reportServiceImpl;

    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {

            Object obj = reportServiceImpl.find(Reports.class,Integer.parseInt(value));

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to inputtype", value)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof Reports)) {
            return null;
        }

        String s = String.valueOf(((Reports) value).getId());

        return s;
    }
}

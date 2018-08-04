/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.SearchService;
import com.drm.model.entities.Trx;
import com.drm.model.entities.TrxFieldsValues;
import com.drm.utils.Logger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mohammed.ayad
 */
@ManagedBean(name = "DynamnicSearchBean")
@ViewScoped
public class DynamnicSearchController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(DynamnicSearchController.class);

    @EJB
    private SearchService searchService;
    private List<String> trxTypeList;
    private String selectedTrxType;
    private List<String> trxFieldsList;
    private List<String> fieldsTypeList;
    private String selectedTrxFiled;
    private BigDecimal fromFieldValue;
    private BigDecimal toFieldValue;
    private String selectedFiledType;
    private String selectedFiledValue;
    private Date fromValueDate, toValueDate;

    private List<Trx> trxs;

    @PostConstruct
    public void init() {
        logger.debug("init.....");
        trxTypeList = new ArrayList<String>();
        trxTypeList = searchService.getAllTrxType();
        logger.debug("trxTypeList " + trxTypeList);
        trxFieldsList = new ArrayList<String>();
        trxFieldsList = searchService.getAllFieldsName();
        logger.debug("trxFieldsList " + trxFieldsList);
        fieldsTypeList = new ArrayList<String>();
        fieldsTypeList = searchService.getAllFieldsType();
        logger.debug("fieldsTypeList " + fieldsTypeList);

    }

    public void doSearch() {
        logger.debug("doSearch with ");
        logger.debug("selectedTrxType " + selectedTrxType);
        logger.debug("selectedTrxFiled " + selectedTrxFiled);
        logger.debug("fromValueDate " + fromValueDate);
        logger.debug("toValueDate " + toValueDate);
        logger.debug("selectedFiledValue " + selectedFiledValue);
        logger.debug("fromFieldValue " + fromFieldValue);
        logger.debug("toFieldValue " + toFieldValue);
        advancedSearch(fromValueDate, toValueDate, selectedTrxType, selectedTrxFiled, fromFieldValue, toFieldValue, selectedFiledValue);
    }

    public void refrech() {
        System.out.println("trxs " + trxs);
    }

    private void advancedSearch(Date from, Date to, String trxType, String trxField,
            BigDecimal fromFieldValue, BigDecimal toFieldValue, String selectedFiledValue) {
        logger.debug("advancedSearch");
        if (trxType != null || trxField != null || from != null || to != null
                || fromFieldValue != null || toFieldValue != null || selectedFiledValue != null) {
            int index = 1;
            List<Date> parmeters = new ArrayList<Date>();

            String sql = "SELECT t FROM TrxFieldsValues t WHERE 1=1 ";
            if (trxType != null) {
                sql += "and t.trxId.tRXtypemsgID.ttype like '%" + trxType + "%' ";

            }
            if (trxField != null) {
                sql += "and t.tRXfieldsID.fName like '%" + trxField + "%' ";
            }
//            if (fromFieldValue != null) {
//                sql += "and t.tRXValuesID >=  '" + trxField + "' ";
//
//            }
//            if (toFieldValue != null) {
//                sql += "and t.tRXValuesID <=  '" + trxField + "' ";
//            }
            if (from != null) {

                sql += " and t.trxId.createDate >=?" + index;
                parmeters.add(from);
                index++;
            }

            if (to != null) {
                sql += " and  t.trxId.createDate <=?" + index;
                parmeters.add(to);
                index++;

            }
            if (selectedFiledValue != null) {
                sql += "and t.tRXValuesID.fValue like '%" + selectedFiledValue + "%' ";
            }
            if (fromFieldValue != null) {

                sql += " and cast(t.tRXValuesID.fValue as unsigned) >=" + fromFieldValue;
            }

            if (toFieldValue != null) {
                sql += " and  cast(t.tRXValuesID.fValue as unsigned) <=" + toFieldValue;

            }
            logger.debug("sql " + sql);
            List<TrxFieldsValues> searchedResult = searchService.getAdvancedSearchResult(sql, parmeters);
            logger.debug("advanced search Result " + searchedResult);

        } else {
            JSFUtils.addErrorMessage("You didn't choose any Filtration");
            return;
        }

    }

    public List<String> getTrxTypeList() {
        return trxTypeList;
    }

    public void setTrxTypeList(List<String> trxTypeList) {
        this.trxTypeList = trxTypeList;
    }

    public String getSelectedTrxType() {
        return selectedTrxType;
    }

    public void setSelectedTrxType(String selectedTrxType) {
        this.selectedTrxType = selectedTrxType;
    }

    public List<String> getTrxFieldsList() {
        return trxFieldsList;
    }

    public void setTrxFieldsList(List<String> trxFieldsList) {
        this.trxFieldsList = trxFieldsList;
    }

    public String getSelectedTrxFiled() {
        return selectedTrxFiled;
    }

    public void setSelectedTrxFiled(String selectedTrxFiled) {
        this.selectedTrxFiled = selectedTrxFiled;
    }

    public Date getFromValueDate() {
        return fromValueDate;
    }

    public void setFromValueDate(Date fromValueDate) {
        this.fromValueDate = fromValueDate;
    }

    public Date getToValueDate() {
        return toValueDate;
    }

    public void setToValueDate(Date toValueDate) {
        this.toValueDate = toValueDate;
    }

    public BigDecimal getFromFieldValue() {
        return fromFieldValue;
    }

    public void setFromFieldValue(BigDecimal fromFieldValue) {
        this.fromFieldValue = fromFieldValue;
    }

    public BigDecimal getToFieldValue() {
        return toFieldValue;
    }

    public void setToFieldValue(BigDecimal toFieldValue) {
        this.toFieldValue = toFieldValue;
    }

    public List<String> getFieldsTypeList() {
        return fieldsTypeList;
    }

    public void setFieldsTypeList(List<String> fieldsTypeList) {
        this.fieldsTypeList = fieldsTypeList;
    }

    public String getSelectedFiledType() {
        return selectedFiledType;
    }

    public void setSelectedFiledType(String selectedFiledType) {
        this.selectedFiledType = selectedFiledType;
    }

    public String getSelectedFiledValue() {
        return selectedFiledValue;
    }

    public void setSelectedFiledValue(String selectedFiledValue) {
        this.selectedFiledValue = selectedFiledValue;
    }

    @Override
    public void auditAction(String actionResult, String actionValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

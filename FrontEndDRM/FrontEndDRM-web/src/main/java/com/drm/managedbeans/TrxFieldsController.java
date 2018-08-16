/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.model.entities.FieldType;
import com.drm.utils.Logger;
import com.drm.utils.UserAction;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.drm.facade.services.TrxFieldsService;
import com.drm.model.entities.TrxFields;
import javax.faces.application.FacesMessage;

/**
 *
 * @author mohammed.ayad
 */
@ManagedBean(name = "trxFieldsBean")
@ViewScoped
public class TrxFieldsController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(TrxFieldsController.class);

    private List<TrxFields> searchedTrxFieldsResult;
    @EJB
    private TrxFieldsService trxFieldsService;
    private TrxFields selectedTrxField;
    private TrxFields newTrxField;
    private boolean allowAdd = true;
    List<String> availableFieldTypeList = Arrays.asList("text", "numeric");

    @PostConstruct
    public void init() {
        logger.debug("FieldsTypeController has been initilized.....");
        refrechTrxFields();

    }

    public void refrechTrxFields() {
        logger.debug("refrechTrxFields");
        try {
            searchedTrxFieldsResult = trxFieldsService.getAllTrxFields();
            logger.debug("Transaction Fields search Result " + searchedTrxFieldsResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all Transaction Fields", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all Transaction Fields", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void viewTrxField() {
        try {
            logger.debug("viewTrxField " + selectedTrxField);
            selectedTrxField = trxFieldsService.find(TrxFields.class, selectedTrxField.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View Transaction Field " + selectedTrxField.getFName(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View Transaction Field " + selectedTrxField.getFName(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteTrxField() {
        try {
            logger.debug("deleteTrxField " + selectedTrxField);
            trxFieldsService.remove(selectedTrxField);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete Transaction Field " + selectedTrxField.getFName(), logger);
            JSFUtils.addInfoMessage("Transaction Field has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechTrxFields();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete Transaction Field " + selectedTrxField.getFName(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void editTrxFieldInfo() {
        logger.debug("editTrxFieldInfo " + selectedTrxField);
        try {
            trxFieldsService.updateFieldTypeInfo(selectedTrxField);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update Transaction Field " + selectedTrxField.getFName(), logger);
            JSFUtils.addInfoMessage("Transaction Field has been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechTrxFields();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update Transaction Field " + selectedTrxField.getFName(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void addNewTrxField() {
        logger.debug("addNewTrxField");
        logger.debug("Field Name " + newTrxField.getFName());
        logger.debug("Field Description " + newTrxField.getFDescription());
        logger.debug("Field Type " + newTrxField.getFieldtypeID().getFtype());
        logger.debug("Field Pattern " + newTrxField.getFieldtypeID().getFpaterren());
        try {
            boolean isTrxFieldNameExistBefore = trxFieldsService.isTrxFieldNameExistBefore(newTrxField.getFName());
            logger.debug("Field Name " + newTrxField.getFName() + " is exist before??? " + isTrxFieldNameExistBefore);
            if (isTrxFieldNameExistBefore) {
                auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Transaction Field " + newTrxField.getFName(), logger);
                JSFUtils.addErrorMessage("Transaction Field already exists!");
                logger.error("Transaction Field already exists!");
                return;
            }

            trxFieldsService.addNewTrxField(newTrxField);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new Transaction Field " + newTrxField.getFName(), logger);
            JSFUtils.addFacesMessage("Transaction Field has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("Transaction Field has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrechTrxFields();
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Transaction Field " + newTrxField.getFName(), logger);
            JSFUtils.addErrorMessage("error happend while adding");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public void prepareNewTrxField() {
        logger.debug("prepareNewTrxField");
        newTrxField = new TrxFields();
        newTrxField.setFieldtypeID(new FieldType());
    }

    public List<TrxFields> getSearchedTrxFieldsResult() {
        return searchedTrxFieldsResult;
    }

    public void setSearchedTrxFieldsResult(List<TrxFields> searchedTrxFieldsResult) {
        this.searchedTrxFieldsResult = searchedTrxFieldsResult;
    }

    public TrxFields getSelectedTrxField() {
        return selectedTrxField;
    }

    public void setSelectedTrxField(TrxFields selectedTrxField) {
        this.selectedTrxField = selectedTrxField;
    }

    public TrxFields getNewTrxField() {
        return newTrxField;
    }

    public void setNewTrxField(TrxFields newTrxField) {
        this.newTrxField = newTrxField;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public List<String> getAvailableFieldTypeList() {
        return availableFieldTypeList;
    }

    public void setAvailableFieldTypeList(List<String> availableFieldTypeList) {
        this.availableFieldTypeList = availableFieldTypeList;
    }

}

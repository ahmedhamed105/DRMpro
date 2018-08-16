/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.FieldsTypeService;
import com.drm.model.entities.FieldType;
import com.drm.utils.Logger;
import com.drm.utils.UserAction;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mohammed.ayad
 */
@ManagedBean(name = "fieldsTypeBean")
@ViewScoped
public class FieldsTypeController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(FieldsTypeController.class);

    private List<FieldType> searchedFieldsTypeResult;
    @EJB
    private FieldsTypeService fieldsTypeService;
    private FieldType selectedFieldType;
    private FieldType newFieldType;
    private boolean allowAdd = true;
    List<String> availableFieldTypeList=Arrays.asList("text", "numeric");

    @PostConstruct
    public void init() {
        logger.debug("FieldsTypeController has been initilized.....");
        refrechFieldsType();

    }

    public void refrechFieldsType() {
        logger.debug("refrechFieldsType");
        try {
            searchedFieldsTypeResult = fieldsTypeService.getAllFieldsType();
            logger.debug("Fields Type search Result " + searchedFieldsTypeResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all Fields Type", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all Fields Type", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void viewFieldType() {
        try {
            logger.debug("viewFieldType " + selectedFieldType);
            selectedFieldType = fieldsTypeService.find(FieldType.class, selectedFieldType.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View Field Type " + selectedFieldType.getFtype() + " Field Pattern " + selectedFieldType.getFpaterren(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View Field Type " + selectedFieldType.getFtype() + " Field Pattern " + selectedFieldType.getFpaterren(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteFieldType() {
        try {
            logger.debug("deleteFieldType " + selectedFieldType);
            fieldsTypeService.remove(selectedFieldType);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete Field Type " + selectedFieldType.getFtype() + " Field Pattern " + selectedFieldType.getFpaterren(), logger);
            JSFUtils.addInfoMessage("Application has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechFieldsType();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete Field Type " + selectedFieldType.getFtype() + " Field Pattern " + selectedFieldType.getFpaterren(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void editFieldTypeInfo() {
        logger.debug("editFieldTypeInfo " + selectedFieldType);
        try {
            fieldsTypeService.updateFieldTypeInfo(selectedFieldType);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update Field Type " + selectedFieldType.getFtype() + " Field Pattern " + selectedFieldType.getFpaterren(), logger);
            JSFUtils.addInfoMessage("Field Type has been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechFieldsType();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update Field Type " + selectedFieldType.getFtype() + " Field Pattern " + selectedFieldType.getFpaterren(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void prepareNewFieldType() {
        logger.debug("prepareNewFieldType");
        newFieldType = new FieldType();
    }

    public List<FieldType> getSearchedFieldsTypeResult() {
        return searchedFieldsTypeResult;
    }

    public void setSearchedFieldsTypeResult(List<FieldType> searchedFieldsTypeResult) {
        this.searchedFieldsTypeResult = searchedFieldsTypeResult;
    }

    public FieldType getSelectedFieldType() {
        return selectedFieldType;
    }

    public void setSelectedFieldType(FieldType selectedFieldType) {
        this.selectedFieldType = selectedFieldType;
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

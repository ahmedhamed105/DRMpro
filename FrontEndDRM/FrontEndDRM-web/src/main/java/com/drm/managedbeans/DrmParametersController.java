/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.DrmParametersService;
import com.drm.model.entities.DrmParameter;
import com.drm.model.entities.User;
import com.drm.utils.Logger;
import com.drm.utils.UserAction;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mohammed.ayad
 */
@ManagedBean(name = "parametersBean")
@ViewScoped
public class DrmParametersController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(DrmParametersController.class);
    private List<DrmParameter> searchedDrmParametersResult;
    private DrmParameter selectedDrmParameter;
    @EJB
    private DrmParametersService drmParametersService;
    private boolean allowAdd = true;
    private DrmParameter newDrmParameter;

    @PostConstruct
    public void init() {
        logger.debug("DrmParametersController has been initilized.....");
        refrechDrmParameters();

    }

    public void refrechDrmParameters() {
        logger.debug("refrechDrmParameters");
        try {
            searchedDrmParametersResult = drmParametersService.getAllDrmParameters();
            logger.debug("DRM Parameters search Result " + searchedDrmParametersResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all DRM Parameters", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all DRM Parameters", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void viewDrmParameter() {
        try {
            logger.debug("viewDrmParameter " + selectedDrmParameter);
            selectedDrmParameter = drmParametersService.find(DrmParameter.class, selectedDrmParameter.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View Drm Parameter " + selectedDrmParameter.getParametername(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View Drm Parameter " + selectedDrmParameter.getParametername(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteDrmParameter() {
        try {
            logger.debug("deleteDrmParameter " + selectedDrmParameter);
            drmParametersService.remove(selectedDrmParameter);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete DRM Parameter " + selectedDrmParameter.getParametername(), logger);
            JSFUtils.addInfoMessage("Parameter has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechDrmParameters();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete DRM Parameter " + selectedDrmParameter.getParametername(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void editDrmParameterInfo() {
        logger.debug("editDrmParameterInfo " + selectedDrmParameter);
        try {
            drmParametersService.updateParameterInfo(selectedDrmParameter);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update DRM Parameter " + selectedDrmParameter.getParametername(), logger);
            JSFUtils.addInfoMessage("Parameter has been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechDrmParameters();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update DRM Parameter " + selectedDrmParameter.getParametername(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void prepareNewDrmParameter() {
        logger.debug("prepareNewDrmParameter");
        newDrmParameter = new DrmParameter();

    }

    public void addNewDrmParameter() {
        logger.debug("addNewDrmParameter");
        User currentUser = getCurrentLoggedInUser();
        logger.debug(currentUser.getUsername() + " has been added new parameter");
        logger.debug("Parameter Name " + newDrmParameter.getParametername());
        logger.debug("Parameter Value " + newDrmParameter.getParameterValue());
        try {
            boolean isParameterNameExistBefore = drmParametersService.isParameterNameExistBefore(newDrmParameter.getParametername());
            logger.debug("Parameter Name " + newDrmParameter.getParametername() + " is exist before??? " + isParameterNameExistBefore);
            if (isParameterNameExistBefore) {
                auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new DRM Parameter " + newDrmParameter.getParametername(), logger);
                JSFUtils.addErrorMessage("Parameter Name already exists!");
                logger.error("Parameter Name already exists!");
                return;
            }

            drmParametersService.addNewParameter(newDrmParameter, currentUser);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new DRM Parameter " + newDrmParameter.getParametername(), logger);
            JSFUtils.addFacesMessage("New Parameter has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("DRM Parameter has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrechDrmParameters();
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new DRM Parameter " + newDrmParameter.getParametername(), logger);
            JSFUtils.addErrorMessage("error happend while adding");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public List<DrmParameter> getSearchedDrmParametersResult() {
        return searchedDrmParametersResult;
    }

    public void setSearchedDrmParametersResult(List<DrmParameter> searchedDrmParametersResult) {
        this.searchedDrmParametersResult = searchedDrmParametersResult;
    }

    public DrmParameter getSelectedDrmParameter() {
        return selectedDrmParameter;
    }

    public void setSelectedDrmParameter(DrmParameter selectedDrmParameter) {
        this.selectedDrmParameter = selectedDrmParameter;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public DrmParameter getNewDrmParameter() {
        return newDrmParameter;
    }

    public void setNewDrmParameter(DrmParameter newDrmParameter) {
        this.newDrmParameter = newDrmParameter;
    }

}

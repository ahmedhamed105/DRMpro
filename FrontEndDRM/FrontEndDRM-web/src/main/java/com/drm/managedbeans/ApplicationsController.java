/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.ApplicationsService;
import com.drm.model.entities.Applications;
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
@ManagedBean(name = "applicationsBean")
@ViewScoped
public class ApplicationsController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(ApplicationsController.class);
    private List<Applications> searchedApplicationsResult;
    @EJB
    ApplicationsService applicationsService;
    private Applications selectedApplication;
    private Applications newApplication;
    private boolean allowAdd = true;

    @PostConstruct
    public void init() {
        logger.debug("ApplicationsController has been initilized.....");
        refrechApplications();

    }

    public void refrechApplications() {
        try {
            searchedApplicationsResult = applicationsService.getAllApplications();
            logger.debug("Applications search Result " + searchedApplicationsResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all Applications", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all Applications", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void viewApplication() {
        try {
            logger.debug("viewApplication " + selectedApplication);
            selectedApplication = applicationsService.find(Applications.class, selectedApplication.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View Application " + selectedApplication.getApplicationsCode(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View Application " + selectedApplication.getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteApplication() {
        try {
            logger.debug("deleteApplication " + selectedApplication);
            applicationsService.remove(selectedApplication);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete Application " + selectedApplication.getApplicationsCode(), logger);
            JSFUtils.addInfoMessage("Application has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechApplications();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete Application " + selectedApplication.getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void editApplicationInfo() {
        logger.debug("editApplicationInfo " + selectedApplication);
        try {
            applicationsService.updateApplicationInfo(selectedApplication);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update Application " + selectedApplication.getApplicationsCode(), logger);
            JSFUtils.addInfoMessage("Application has been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechApplications();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update Application " + selectedApplication.getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void prepareNewApplication() {
        newApplication = new Applications();
    }

    public void addNewApplication() {
        logger.debug("addNewApplication");
        logger.debug("Application Code " + newApplication.getApplicationsCode());
        logger.debug("Application Description " + newApplication.getApplicationDesc());

//        boolean isApplicationsCodeExistBefore = applicationsService.isApplicationsCodeExistBefore(newApplication.getApplicationsCode());
//        logger.debug("Application Code " + newApplication.getApplicationsCode() + " is exist before??? " + isApplicationsCodeExistBefore);
//        if (isUserNameExistBefore) {
//            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Application " + newApplication.getApplicationsCode(), logger);
//            JSFUtils.addErrorMessage("Application Code already exists!");
//            logger.error("Application Code already exists!");
//            return;
//        }
        try {
            applicationsService.addNewApplication(newApplication);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new Application " + newApplication.getApplicationsCode(), logger);
            JSFUtils.addFacesMessage("Application has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("Application has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrechApplications();
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Application " + newApplication.getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while adding");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public List<Applications> getSearchedApplicationsResult() {
        return searchedApplicationsResult;
    }

    public void setSearchedApplicationsResult(List<Applications> searchedApplicationsResult) {
        this.searchedApplicationsResult = searchedApplicationsResult;
    }

    public Applications getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(Applications selectedApplication) {
        this.selectedApplication = selectedApplication;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public Applications getNewApplication() {
        return newApplication;
    }

    public void setNewApplication(Applications newApplication) {
        this.newApplication = newApplication;
    }

}

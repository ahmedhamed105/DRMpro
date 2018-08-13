/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.ApplicationUserService;
import com.drm.model.entities.ApplicationUser;
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
@ManagedBean(name = "applicationUserBean")
@ViewScoped
public class ApplicationUserController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(ApplicationUserController.class);
    private List<ApplicationUser> searchedApplicationUserResult;
    private ApplicationUser selectedApplicationUser;
    @EJB
    private ApplicationUserService applicationUserService;
    private ApplicationUser newApplicationUser;
    private boolean allowAdd = true;
    private List<String> userNameList;
    private List<String> appCodesList;
    private String selectedUserName;
    private String selectedApplicationCode;

    @PostConstruct
    public void init() {
        refrechApplicationUser();
        logger.debug("ApplicationUserController has been initilized.....");
        userNameList = applicationUserService.getAllAvaiableUserNames();
        logger.debug("userNameList " + userNameList);
        appCodesList = applicationUserService.getAllAvaiableApplicationCodes();
        logger.debug("appCodesList " + appCodesList);

    }

    public void refrechApplicationUser() {
        try {
            searchedApplicationUserResult = applicationUserService.getAllApplicationUser();
            logger.debug("Application User search Result " + searchedApplicationUserResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all Application User", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all Application User", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void addNewApplicationUser() {
        logger.debug("addNewApplicationUser");
        logger.debug("selectedUserName " + selectedUserName);
        logger.debug("selectedApplicationCode " + selectedApplicationCode);
//        logger.debug("User Name " + newApplicationUser.getUserID().getUsername());
//        logger.debug("Application Code " + newApplicationUser.getApplicationsID().getApplicationsCode());

//        boolean isApplicationsCodeExistBefore = applicationsService.isApplicationsCodeExistBefore(newApplication.getApplicationsCode());
//        logger.debug("Application Code " + newApplication.getApplicationsCode() + " is exist before??? " + isApplicationsCodeExistBefore);
//        if (isUserNameExistBefore) {
//            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Application " + newApplication.getApplicationsCode(), logger);
//            JSFUtils.addErrorMessage("Application Code already exists!");
//            logger.error("Application Code already exists!");
//            return;
//        }
        if (selectedUserName == null || selectedApplicationCode == null) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add empty user and application ", logger);
            JSFUtils.addErrorMessage("can not add empty user and application!");
            logger.error("can not add empty user and application!");
            selectedUserName = null;
            selectedApplicationCode = null;
            return;
        }
        boolean isApplicationsUserExistBefore = applicationUserService.isApplicationUserExistBefore(selectedUserName, selectedApplicationCode);
        if (isApplicationsUserExistBefore) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Application User " + selectedUserName + " code " + selectedApplicationCode, logger);
            JSFUtils.addErrorMessage("Application User already exists!");
            logger.error("Application User already exists!");
            selectedUserName = null;
            selectedApplicationCode = null;
            return;
        }
        try {
            applicationUserService.addNewApplicationUser(newApplicationUser, selectedUserName, selectedApplicationCode);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new Application User " + newApplicationUser.getUserID().getUsername() + " code " + newApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addFacesMessage("Application User has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("Application has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrechApplicationUser();
            selectedUserName = null;
            selectedApplicationCode = null;
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Application User " + newApplicationUser.getUserID().getUsername() + " code " + newApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while adding");
            logger.error(e);
            logger.error(e.getMessage());
            selectedUserName = null;
            selectedApplicationCode = null;
            return;
        }
    }

    public void viewApplicationUser() {
        try {
            logger.debug("viewApplicationUser " + selectedApplicationUser);
            selectedApplicationUser = applicationUserService.find(ApplicationUser.class, selectedApplicationUser.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View Application User " + selectedApplicationUser.getUserID().getUsername() + " code " + selectedApplicationUser.getApplicationsID().getApplicationsCode(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View Application User " + selectedApplicationUser.getUserID().getUsername() + " code " + selectedApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void editApplicationUserInfo() {
        logger.debug("editApplicationUserInfo " + selectedApplicationUser);
        logger.debug("editApplicationUserInfo " + selectedApplicationUser.getApplicationsID().getApplicationsCode());
        logger.debug("editApplicationUserInfo " + selectedApplicationUser.getUserID().getUsername());
        try {
            applicationUserService.updateApplicationUserInfo(selectedApplicationUser,selectedApplicationUser.getUserID().getUsername(),selectedApplicationUser.getApplicationsID().getApplicationsCode());
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update Application user " + selectedApplicationUser.getUserID().getUsername() + " code " + selectedApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addInfoMessage("Application has been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechApplicationUser();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update Application user " + selectedApplicationUser.getUserID().getUsername() + " code " + selectedApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteApplicationUser() {
        try {
            logger.debug("deleteApplicationUser " + selectedApplicationUser);
            applicationUserService.remove(selectedApplicationUser);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete Application User " + selectedApplicationUser.getUserID().getUsername() + " code " + selectedApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addInfoMessage("Application has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechApplicationUser();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete Application User " + selectedApplicationUser.getUserID().getUsername() + " code " + selectedApplicationUser.getApplicationsID().getApplicationsCode(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void prepareNewApplicationUser() {
        newApplicationUser = new ApplicationUser();
    }

    public List<ApplicationUser> getSearchedApplicationUserResult() {
        return searchedApplicationUserResult;
    }

    public void setSearchedApplicationUserResult(List<ApplicationUser> searchedApplicationUserResult) {
        this.searchedApplicationUserResult = searchedApplicationUserResult;
    }

    public ApplicationUser getSelectedApplicationUser() {
        return selectedApplicationUser;
    }

    public void setSelectedApplicationUser(ApplicationUser selectedApplicationUser) {
        this.selectedApplicationUser = selectedApplicationUser;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }

    public List<String> getAppCodesList() {
        return appCodesList;
    }

    public void setAppCodesList(List<String> AppCodesList) {
        this.appCodesList = AppCodesList;
    }

    public ApplicationUser getNewApplicationUser() {
        return newApplicationUser;
    }

    public void setNewApplicationUser(ApplicationUser newApplicationUser) {
        this.newApplicationUser = newApplicationUser;
    }

    public String getSelectedUserName() {
        return selectedUserName;
    }

    public void setSelectedUserName(String selectedUserName) {
        this.selectedUserName = selectedUserName;
    }

    public String getSelectedApplicationCode() {
        return selectedApplicationCode;
    }

    public void setSelectedApplicationCode(String selectedApplicationCode) {
        this.selectedApplicationCode = selectedApplicationCode;
    }

}

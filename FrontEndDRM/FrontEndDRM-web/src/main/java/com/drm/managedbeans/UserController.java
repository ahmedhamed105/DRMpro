/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.SecurityService;
import com.drm.facade.services.UserService;
import com.drm.model.entities.User;
import com.drm.model.entities.UserPassword;
import com.drm.utils.Logger;
import com.drm.utils.UserAction;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author mohammed.ayad
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UserController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(UserController.class);
    private User user;
    @EJB
    private UserService userService;
    private List<User> searchedUsersResult;
    private User newUser;
    private User selectedUser;
    private boolean allowAdd = true;
    private String password;
    private String confirmPassword;

    @PostConstruct
    public void init() {
        logger.debug("UserController has been initilized.....");
        user = new User();

    }

    public void doSearch() {
        try {
            logger.debug("doSearch with ");
            logger.debug("User Name " + user.getUsername());
            logger.debug("User First Name " + user.getFirstName());
            logger.debug("User Middle Name " + user.getMiddlename());
            logger.debug("User Last Name " + user.getLastname());
            usersSearch();
        } catch (Exception e) {
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public void refrech() {
        try {
            searchedUsersResult = userService.getAllUsers();
            logger.debug("users search Result " + searchedUsersResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all users", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all users", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void prepareNewUser() {
        newUser = new User();
    }

    private void usersSearch() {
        logger.debug("usersSearch");
        if (!user.getUsername().equalsIgnoreCase("") || !user.getFirstName().equalsIgnoreCase("")
                || !user.getMiddlename().equalsIgnoreCase("") || !user.getLastname().equalsIgnoreCase("")) {
            String jpql = "SELECT u FROM User u WHERE 1=1 ";
            if (!user.getUsername().equalsIgnoreCase("")) {
                jpql += "and u.username like '%" + user.getUsername() + "%' ";

            }
            if (!user.getFirstName().equalsIgnoreCase("")) {
                jpql += "and u.firstName like '%" + user.getFirstName() + "%' ";

            }
            if (!user.getMiddlename().equalsIgnoreCase("")) {
                jpql += "and u.middlename like '%" + user.getMiddlename() + "%' ";

            }
            if (!user.getLastname().equalsIgnoreCase("")) {
                jpql += "and u.lastname like '%" + user.getLastname() + "%' ";

            }

            logger.debug("jpql " + jpql);
            searchedUsersResult = userService.searchUsers(jpql);
            logger.debug("users search Result " + searchedUsersResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success User Search", logger);

        } else {
            JSFUtils.addErrorMessage("You didn't choose any Filtration");
            logger.debug("You didn't choose any Filtration ");
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed User Search didn't choose any Filtration", logger);

            return;
        }

    }

    public void addUser() {
        logger.debug("addUser");
        logger.debug("User Name " + newUser.getUsername());
        logger.debug("password " + password);
        logger.debug("confirmPassword " + confirmPassword);
        if (!password.equals(confirmPassword)) {
            JSFUtils.addErrorMessage("password and Confirmed password must be the same");
            logger.error("password and Confirmed password must be the same");
            return;
        }

        boolean isUserNameExistBefore = userService.isUserNameExistBefore(newUser.getUsername());
        logger.debug("user name " + newUser.getUsername() + " is exist before??? " + isUserNameExistBefore);
        if (isUserNameExistBefore) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new User " + newUser.getUsername(), logger);
            JSFUtils.addErrorMessage("User Name already exists!");
            logger.error("User Name already exists!");
            return;
        }
        try {
            userService.addNewUser(newUser, password);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new User " + newUser.getUsername(), logger);
            JSFUtils.addFacesMessage("User has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("User has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrech();
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new User " + newUser.getUsername(), logger);
            JSFUtils.addErrorMessage("error happend while inserting");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public void viewUser() {
        try {
            logger.debug("viewUser " + selectedUser);
            selectedUser = userService.find(User.class, selectedUser.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View User " + selectedUser.getUsername(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View User " + selectedUser.getUsername(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void editUserInfo() {
        logger.debug("editUserInfo " + selectedUser);
        if (password != null && !password.trim().isEmpty()) {
            if (!password.equals(confirmPassword)) {
                auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update User " + selectedUser.getUsername(), logger);
                logger.error("New password and Confirmed password must be the same");
                JSFUtils.addInfoMessage(
                        "New password and Confirmed password must be the same");
                return;
            }
        }
        try {
            userService.updateUserInfo(selectedUser, password);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update User " + selectedUser.getUsername(), logger);
            JSFUtils.addInfoMessage("User have been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrech();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update User " + selectedUser.getUsername(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteUser() {
        try {
            logger.debug("deleteUser " + selectedUser);
            userService.remove(selectedUser);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete User " + selectedUser.getUsername(), logger);
            JSFUtils.addInfoMessage("User has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrech();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete User " + selectedUser.getUsername(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

//    @Override
//    public void auditAction(String actionResult, String actionValue) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getSearchedUserResult() {
        return searchedUsersResult;
    }

    public void setSearchedUserResult(List<User> searchedUserResult) {
        this.searchedUsersResult = searchedUserResult;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

}

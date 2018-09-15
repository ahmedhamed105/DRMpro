/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.DrmParametersService;
import com.drm.facade.services.TerminalService;
import com.drm.model.entities.DrmParameter;
import com.drm.model.entities.Terminal;
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
 * @author ahmed.elemam
 */
@ManagedBean(name = "termianlbean")
@ViewScoped
public class Terminals  extends AbstractManagedBean{
    
     private static final Logger logger = Logger.getLogger(Terminals.class);
    private List<Terminal> searchedTerminalResult;
    private Terminal selectedTerminal;
    @EJB
    private TerminalService termminalServiceImpl;
    private boolean allowAdd = true;
    private Terminal newTerminal;

    

    
        @PostConstruct
    public void init() {
        logger.debug("Terminal has been initilized.....");
        refrechDrmParameters();

    }

    public void refrechDrmParameters() {
        logger.debug("refrechTerminal");
        try {
            searchedTerminalResult = termminalServiceImpl.getall();
            logger.debug("Terminal search Result " + searchedTerminalResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all terminals", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all terminals", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void viewDrmParameter() {
        try {
            logger.debug("viewDrmParameter " + selectedTerminal);
            selectedTerminal = termminalServiceImpl.find(Terminal.class, selectedTerminal.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success ViewselectedTerminal" + selectedTerminal.getTid(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View selectedTerminal " + selectedTerminal.getTid(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void deleteDrmParameter() {
        try {
            logger.debug("deleteDrmParameter " + selectedTerminal);
            termminalServiceImpl.remove(selectedTerminal);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete selectedTerminal " + selectedTerminal.getTid(), logger);
            JSFUtils.addInfoMessage("Parameter has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechDrmParameters();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete terminal " + selectedTerminal.getTid(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void editDrmParameterInfo() {
        logger.debug("editDrmParameterInfo " + selectedTerminal);
        try {
            termminalServiceImpl.updateParameterInfo(selectedTerminal);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update terminal " + selectedTerminal.getTid(), logger);
            JSFUtils.addInfoMessage("Parameter has been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechDrmParameters();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update terminal " + selectedTerminal.getTid(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void prepareNewDrmParameter() {
        logger.debug("prepareNewDrmParameter");
        newTerminal = new Terminal();

    }

    public void addNewDrmParameter() {
        logger.debug("addNewDrmParameter");
        User currentUser = getCurrentLoggedInUser();
        logger.debug(currentUser.getUsername() + " has been added new parameter");
        logger.debug("tid " + newTerminal.getTid());
       
        try {
            boolean isParameterNameExistBefore = termminalServiceImpl.isParameterNameExistBefore(newTerminal.getTid());
            logger.debug("Parameter Name " + newTerminal.getTid()+ " is exist before??? " + isParameterNameExistBefore);
            if (isParameterNameExistBefore) {
                auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Terminal " + newTerminal.getTid(), logger);
                JSFUtils.addErrorMessage("Parameter Name already exists!");
                logger.error("Parameter Name already exists!");
                return;
            }

            termminalServiceImpl.addNewParameter(newTerminal);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new Terminal " + newTerminal.getTid(), logger);
            JSFUtils.addFacesMessage("New Parameter has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("terminal has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrechDrmParameters();
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Terminal " + newTerminal.getTid(), logger);
            JSFUtils.addErrorMessage("error happend while adding");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public List<Terminal> getSearchedTerminalResult() {
        return searchedTerminalResult;
    }

    public void setSearchedTerminalResult(List<Terminal> searchedTerminalResult) {
        this.searchedTerminalResult = searchedTerminalResult;
    }

    public Terminal getSelectedTerminal() {
        return selectedTerminal;
    }

    public void setSelectedTerminal(Terminal selectedTerminal) {
        this.selectedTerminal = selectedTerminal;
    }
    
    

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public Terminal getNewTerminal() {
        return newTerminal;
    }

    public void setNewTerminal(Terminal newTerminal) {
        this.newTerminal = newTerminal;
    }

    
    
     
    
}

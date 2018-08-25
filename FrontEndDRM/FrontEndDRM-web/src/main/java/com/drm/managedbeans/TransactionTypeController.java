/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.TransactionTypeService;
import com.drm.model.entities.TrxTypeMsg;
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
@ManagedBean(name = "transactionBean")
@ViewScoped
public class TransactionTypeController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(TransactionTypeController.class);
    private List<TrxTypeMsg> searchedTransactionTypeResult;
    @EJB
    private TransactionTypeService transactionTypeService;
    private boolean allowAdd = true;
    private TrxTypeMsg selectedTransactionType;
    private TrxTypeMsg newTransactionType;

    @PostConstruct
    public void init() {
        logger.debug("TransactionTypeController has been initilized.....");
        refrechTransactionTypes();

    }

    public void refrechTransactionTypes() {
        logger.debug("refrechTransactionTypes");
        try {
            searchedTransactionTypeResult = transactionTypeService.getAllTransactionTypes();
            logger.debug("Transaction Types search Result " + searchedTransactionTypeResult);
            auditAction(UserAction.INQUIRY, UserAction.SUCCESS.name(), "Success Search for all Transaction Types", logger);
        } catch (Exception e) {
            auditAction(UserAction.INQUIRY, UserAction.FAIL.name(), "Failed Search for all Transaction Types", logger);
            JSFUtils.addErrorMessage("error happend while searching");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void viewTransactionType() {
        try {
            logger.debug("viewTransactionType " + selectedTransactionType);
            selectedTransactionType = transactionTypeService.find(TrxTypeMsg.class, selectedTransactionType.getId());
            auditAction(UserAction.VIEW, UserAction.SUCCESS.name(), "Success View Transaction Type " + selectedTransactionType.getTtype(), logger);
        } catch (Exception e) {
            auditAction(UserAction.VIEW, UserAction.FAIL.name(), "Failed View Transaction Type " + selectedTransactionType.getTtype(), logger);
            JSFUtils.addErrorMessage("error happend while Viewing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void prepareNewTransactionType() {
        logger.debug("prepareNewTransactionType");
        newTransactionType = new TrxTypeMsg();

    }

    public void deleteTransactionType() {
        try {
            logger.debug("deleteTransactionType " + selectedTransactionType);
            User currentUser = getCurrentLoggedInUser();
            transactionTypeService.deleteTransactionType(currentUser, selectedTransactionType);
            auditAction(UserAction.DELETE, UserAction.SUCCESS.name(), "Success Delete Transaction Type " + selectedTransactionType.getTtype(), logger);
            JSFUtils.addInfoMessage("Transaction Type has been deleted successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechTransactionTypes();
        } catch (Exception e) {
            auditAction(UserAction.DELETE, UserAction.FAIL.name(), "Failed Delete Transaction Type " + selectedTransactionType.getTtype(), logger);
            JSFUtils.addErrorMessage("error happend while deleting");
            logger.error(e);
            logger.error(e.getMessage());
            return;

        }

    }

    public void editTransactionTypeInfo() {
        logger.debug("editTransactionTypeInfo " + selectedTransactionType);
        try {
            transactionTypeService.updateTrxTypeInfo(selectedTransactionType);
            auditAction(UserAction.MODIFY, UserAction.SUCCESS.name(), "Success Update Transaction Type " + selectedTransactionType.getTtype(), logger);
            JSFUtils.addInfoMessage("Transaction Type been updated successfully");
            JSFUtils.executeClientAction("PF('dlg1').hide();");
            refrechTransactionTypes();
        } catch (Exception e) {
            auditAction(UserAction.MODIFY, UserAction.FAIL.name(), "Failed Update Transaction Type " + selectedTransactionType.getTtype(), logger);
            JSFUtils.addErrorMessage("error happend while editing");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }

    }

    public void addNewTrxType() {
        logger.debug("addNewTrxType");
        try {
            boolean isTrxTypeExistBefore = transactionTypeService.isTrxTypeExistBefore(newTransactionType.getTtype());
            logger.debug("transaction Type " + newTransactionType.getTtype() + " is exist before??? " + isTrxTypeExistBefore);
            if (isTrxTypeExistBefore) {
                auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Transaction Type " + newTransactionType.getTtype(), logger);
                JSFUtils.addErrorMessage("Transaction Type already exists!");
                logger.error("Transaction Type already exists!");
                return;
            }

            transactionTypeService.addNewTrxType(newTransactionType);
            auditAction(UserAction.ADD, UserAction.SUCCESS.name(), "Success Add new Transaction Type " + newTransactionType.getTtype(), logger);
            JSFUtils.addFacesMessage("Transaction Type has been added successfully!", FacesMessage.SEVERITY_INFO);
            logger.debug("Transaction Type has been added successfully!");
            JSFUtils.executeClientAction("PF('dlg2').hide();");
            refrechTransactionTypes();
        } catch (Exception e) {
            auditAction(UserAction.ADD, UserAction.FAIL.name(), "Failed Add new Transaction Type " + newTransactionType.getTtype(), logger);
            JSFUtils.addErrorMessage("error happend while adding");
            logger.error(e);
            logger.error(e.getMessage());
            return;
        }
    }

    public List<TrxTypeMsg> getSearchedTransactionTypeResult() {
        return searchedTransactionTypeResult;
    }

    public void setSearchedTransactionTypeResult(List<TrxTypeMsg> searchedTransactionTypeResult) {
        this.searchedTransactionTypeResult = searchedTransactionTypeResult;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public TrxTypeMsg getSelectedTransactionType() {
        return selectedTransactionType;
    }

    public void setSelectedTransactionType(TrxTypeMsg selectedTransactionType) {
        this.selectedTransactionType = selectedTransactionType;
    }

    public TrxTypeMsg getNewTransactionType() {
        return newTransactionType;
    }

    public void setNewTransactionType(TrxTypeMsg newTransactionType) {
        this.newTransactionType = newTransactionType;
    }

}

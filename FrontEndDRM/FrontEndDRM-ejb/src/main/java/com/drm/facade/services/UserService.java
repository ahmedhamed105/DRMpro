/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.User;
import java.util.List;

/**
 *
 * @author mohammed.ayad
 */
public interface UserService extends DataService {

    List<User> searchUsers(String jpql);
    List<User> getAllUsers();
    boolean isUserNameExistBefore(String userName);
    void addNewUser(User user,String password) throws Exception;
    void updateUserInfo(User user,String updatedPassword) throws Exception;
    List<String> getAllUserNames();
    List<User> getUserByUserName(String userName);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author awarsyle
 */
public class AccountService {
    
    public User checkLogin(String username, String password, String path) {
        User user;
        
        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);
            
            if (user.getPassword().equals(password)) {
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO, 
                        "A user logged in: {0}",
                        username);
                try {
                    //WebMailService.sendMail(user.getEmail(), "NotesKeepr Logged in", "<h2>Congrats!  You just loggedin successfully.</h2>" , true);
                    
                    HashMap<String, String> contents = new HashMap<>();
                    
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", (new java.util.Date()).toString());
                    
                    String template = path + "/emailtemplates/login.html";
                    WebMailService.sendMail(user.getEmail(), "NotesKeepr Login", template, contents);
                    
                } catch (Exception ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
                return user;
            }
            
        } catch (NotesDBException ex) {
        }
        
        return null;
    }
    
    public User resetPassword(String email,String path, String link, String uuid){
        User user = null;
        
        UserDB userDB = new UserDB();
        try {
           user = userDB.getEmail(email);
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        String username = null;
        username = user.getUsername();
        try {
            user = userDB.getUser(username);
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                try {
                    //WebMailService.sendMail(user.getEmail(), "NotesKeepr Logged in", "<h2>Congrats!  You just loggedin successfully.</h2>" , true);
                    user.setResetPasswordUUID(uuid);
                    userDB.update(user);

                    HashMap<String, String> contents = new HashMap<>();
                    
                    contents.put("firstname", user.getFirstname());
                    contents.put("lastname", user.getLastname());
                    contents.put("username", user.getUsername());
                    contents.put("link", link);
                    
                    String template = path + "/emailtemplates/resetpassword.html";
                    WebMailService.sendMail(user.getEmail(), "NotesKeepr Reset", template, contents);
                    
                } catch (Exception ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
                return user;

            }
        
        
        
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.AccountService;
import businesslogic.UserService;
import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 727334
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }else{
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        String link = request.getRequestURL().toString() + "?uuid=" + uuid;
        String email = request.getParameter("email");
        String action = request.getParameter("action");
        if (action.equals("email")) {
            if (email.isEmpty()) {
                request.setAttribute("errormessager", "fill in both fields, please");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp/").forward(request, response);
            }

            AccountService as = new AccountService();
            String path = getServletContext().getRealPath("/WEB-INF");
            if (as.resetPassword(email, path, link, uuid) != null) {
                HttpSession session = request.getSession();
                session.setAttribute("uuid", uuid);
                request.setAttribute("message", "Email Sent!");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            } else {
                request.setAttribute("errormessager", "invalid login");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp/").forward(request, response);
            }
        } else if (action.equals("reset")) {
            String uuidNew = request.getParameter("uuid");
            String newPass = request.getParameter("pass");
            User user = new User();
            UserService us = new UserService();
            UserDB ud = new UserDB();
            try {
                user = ud.getUuid(uuidNew);
            } catch (NotesDBException ex) {
                Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           //user.setPassword(newPass);
            try {
                us.update(user.getUsername(), newPass, user.getEmail(), true, user.getFirstname(), user.getLastname());
            } catch (Exception ex) {
                Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
           request.setAttribute("errormessage", "Password Reset!");
           getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

}

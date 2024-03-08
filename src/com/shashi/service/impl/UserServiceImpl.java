package com.shashi.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shashi.beans.UserBean;
import com.shashi.beans.UserRoleBean;
import com.shashi.constants.IUserConstants;
import com.shashi.dao.UserDao;
import com.shashi.dao.UserRoleDao;
import com.shashi.dao.impl.UserDaoImpl;
import com.shashi.dao.impl.UserRoleDaoImpl;
import com.shashi.service.UserService;
import com.shashi.utility.DBUtil;
import com.shashi.utility.MailMessage;

public class UserServiceImpl implements UserService {

    @Override
    public String registerUser(UserBean user) {
        String status = "User Registration Failed!";

        boolean isRegistered = isRegistered(user.getEmail());
        if (isRegistered) {
            status = "Email Id Already Registered!";
            return status;
        }

        UserDao userDao = new UserDaoImpl();
        UserBean newUser = userDao.insert(user);
        if (newUser != null) {
            UserRoleDao userRole = new UserRoleDaoImpl();
            userRole.insert(new UserRoleBean(newUser.getEmail(), 2));     // DEFAULT: 2 is the role id for user
            status = "User Registered Successfully!";
//            MailMessage.registrationSuccess(user.getEmail(), user.getName().split(" ")[0]);
        }

        return status;
    }

    @Override
    public boolean isRegistered(String emailId) {
        UserDaoImpl userDao = new UserDaoImpl();
        UserBean user = userDao.findByEmail(emailId);
        return user != null;
    }

    @Override
    public String isValidCredential(String emailId, String password) {
        String status = "Login Denied! Incorrect Username or Password";

        UserDaoImpl userDao = new UserDaoImpl();
        UserBean user = userDao.findByEmailAndPassword(emailId, password);
        if (user != null && user.isActive()) {
            status = "valid";
        }

        return status;
    }

    @Override
    public UserBean getUserDetails(String emailId, String password) {
        UserDaoImpl userDao = new UserDaoImpl();
        return userDao.findByEmailAndPassword(emailId, password);
    }

    @Override
    public String getFName(String emailId) {
        UserDaoImpl userDao = new UserDaoImpl();
        return userDao.findByEmail(emailId).getName().split(" ")[0];
    }

    @Override
    public String getUserAddr(String userId) {
        UserDaoImpl userDao = new UserDaoImpl();
        return userDao.findByEmail(userId).getAddress();
    }

    @Override
    public UserRoleBean[] getUserRole(String emailId) {
        UserRoleDao userRoleDao = new UserRoleDaoImpl();
        return userRoleDao.findByEmail(emailId);
    }

}

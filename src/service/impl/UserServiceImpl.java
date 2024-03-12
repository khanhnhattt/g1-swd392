package service.impl;

import beans.UserBean;
import beans.UserRoleBean;
import constants.IUserConstants;
import dao.UserDaoImpl;
import dao.UserRoleDaoImpl;
import service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public String registerUser(UserBean user) {
        String status = "User Registration Failed!";

        boolean isRegistered = isRegistered(user.getEmail());
        if (isRegistered) {
            status = "Email Id Already Registered!";
            return status;
        }

        UserDaoImpl userDao = new UserDaoImpl();
        UserBean newUser = userDao.insert(user);
        if (newUser != null) {
            UserRoleDaoImpl userRole = new UserRoleDaoImpl();
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
        UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl();
        return userRoleDao.findByEmail(emailId);
    }

}

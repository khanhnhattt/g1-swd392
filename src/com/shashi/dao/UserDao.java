package com.shashi.dao;

import com.shashi.beans.UserBean;

public interface UserDao {
    UserBean findByEmailAndPassword(String email, String password);

    UserBean findByEmail(String email);

    UserBean insert(UserBean user);
}

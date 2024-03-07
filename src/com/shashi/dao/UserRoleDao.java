package com.shashi.dao;

import com.shashi.beans.UserRoleBean;

public interface UserRoleDao {
    UserRoleBean[] findByEmail(String emailId);

    UserRoleBean insert(UserRoleBean userRole);
}
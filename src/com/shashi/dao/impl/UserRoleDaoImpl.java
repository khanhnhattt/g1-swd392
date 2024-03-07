package com.shashi.dao.impl;

import com.shashi.beans.UserRoleBean;
import com.shashi.dao.UserRoleDao;
import com.shashi.utility.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDaoImpl implements UserRoleDao {

    @Override
    public UserRoleBean[] findByEmail(String email) {
        UserRoleBean[] userRole = null;
        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("sect * from user_role where email=?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            rs.last();
            userRole = new UserRoleBean[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                userRole[i] = new UserRoleBean();
                userRole[i].setUserEmail(rs.getString("email"));
                userRole[i].setRoleId(rs.getInt("role_id"));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeConnection(con);
        DBUtil.closeConnection(ps);
        DBUtil.closeConnection(rs);
        return userRole;
    }

    @Override
    public UserRoleBean insert(UserRoleBean userRole) {
        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("insert into user_role values(?,?)");
            ps.setString(1, userRole.getUserEmail());
            ps.setInt(2, userRole.getRoleId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        DBUtil.closeConnection(con);
        DBUtil.closeConnection(ps);
        return userRole;
    }
}

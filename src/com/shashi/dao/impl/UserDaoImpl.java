package com.shashi.dao.impl;

import com.shashi.beans.UserBean;
import com.shashi.dao.UserDao;
import com.shashi.utility.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public UserBean findByEmailAndPassword(String email, String password) {
        UserBean user = new UserBean();
        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("select * from user where email=? and password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setName(rs.getString("name"));
                user.setMobile(rs.getLong("mobile"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPinCode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("isactive"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeConnection(con);
        DBUtil.closeConnection(ps);
        DBUtil.closeConnection(rs);
        return user;
    }

    @Override
    public UserBean findByEmail(String email) {
        UserBean user = new UserBean();
        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("select * from user where email=?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setName(rs.getString("name"));
                user.setMobile(rs.getLong("mobile"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPinCode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("isactive"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeConnection(con);
        DBUtil.closeConnection(ps);
        DBUtil.closeConnection(rs);
        return user;
    }

    @Override
    public UserBean insert(UserBean user) {
        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("insert into user values(?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getName());
            ps.setLong(2, user.getMobile());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAddress());
            ps.setInt(5, user.getPinCode());
            ps.setString(6, user.getPassword());
            ps.setBoolean(7, user.isActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        DBUtil.closeConnection(con);
        DBUtil.closeConnection(ps);
        return user;
    }
}

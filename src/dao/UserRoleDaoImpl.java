package dao;

import beans.UserRoleBean;
import dao.UserRoleDaoImpl;
import utility.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDaoImpl {

    public UserRoleBean[] findByEmail(String email) {
        UserRoleBean[] userRole = null;
        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("select * from user_role where user_email=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, email);
            rs = ps.executeQuery();
            rs.last();
            userRole = new UserRoleBean[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                userRole[i] = new UserRoleBean();
                userRole[i].setUserEmail(rs.getString("user_email"));
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

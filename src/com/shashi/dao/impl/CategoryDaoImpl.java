package com.shashi.dao.impl;

import com.shashi.beans.CategoryBean;
import com.shashi.beans.ProductBean;
import com.shashi.utility.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl {
    public List<CategoryBean> GetListCategory(){
        List<CategoryBean> categories = new ArrayList<CategoryBean>();

        Connection con = DBUtil.provideConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("select * from category");

            rs = ps.executeQuery();

            while (rs.next()) {

                CategoryBean cate = new CategoryBean();

                cate.setCateId(rs.getInt(1));
                cate.setCateName(rs.getString(2));


                categories.add(cate);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBUtil.closeConnection(con);
        DBUtil.closeConnection(ps);
        DBUtil.closeConnection(rs);

        return categories;
    }
}

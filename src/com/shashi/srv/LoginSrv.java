package com.shashi.srv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shashi.beans.UserBean;
import com.shashi.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/LoginSrv")
public class LoginSrv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginSrv() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html");

        UserServiceImpl udao = new UserServiceImpl();
        String status = udao.isValidCredential(userName, password);
        if (status.equalsIgnoreCase("valid")) {
            // valid user
            UserBean user = udao.getUserDetails(userName, password);
            HttpSession session = request.getSession();
            session.setAttribute("userdata", user);

            session.setAttribute("username", user.getEmail());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("usertype", user.getUserType());

            RequestDispatcher rd = null;
            if (user.getUserType().equals("admin")) {
                rd = request.getRequestDispatcher("adminViewProduct.jsp");
            } else {
                rd = request.getRequestDispatcher("userHome.jsp");
            }
            rd.forward(request, response);
        } else {
            // invalid user;
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp?message=" + status);
            rd.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

package srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.DemandBean;
import beans.ProductBean;
import service.impl.CartServiceImpl;
import service.impl.DemandServiceImpl;
import service.impl.ProductServiceImpl;

/**
 * Servlet implementation class UpdateToCart
 */
@WebServlet("/UpdateToCart")
public class UpdateToCart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateToCart() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        String prodId = request.getParameter("pid");
        String sessId = session.getId();
        CartServiceImpl cart = new CartServiceImpl();
//
        if (userName != null || password != null) {
            cart.deleteProductFromCart(userName, prodId);
        } else {
            cart.deleteProductFromGuestCart(sessId, prodId);
            RequestDispatcher rd = request.getRequestDispatcher("cartDetails.jsp");
            rd.include(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}

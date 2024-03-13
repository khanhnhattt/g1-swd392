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

import service.impl.OrderServiceImpl;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userName = (request.getParameter("username") != null) ? request.getParameter("username") : (String) session.getAttribute("username");

        double paidAmount = Double.parseDouble(request.getParameter("amount"));
        String status = new OrderServiceImpl().paymentSuccess(userName, paidAmount);

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        RequestDispatcher rd = request.getRequestDispatcher("orderDetails.jsp");

        rd.include(request, response);

        pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}

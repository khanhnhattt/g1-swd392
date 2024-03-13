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

import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;

import org.json.*;

/**
 * Servlet implementation class AddtoCart
 */
@WebServlet("/AddtoCart")
public class AddtoCart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddtoCart() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        String usertype = (String) session.getAttribute("usertype");

        // login Check Successfull

        String userId = userName;
        String prodId = request.getParameter("pid");
        int pQty = Integer.parseInt(request.getParameter("pqty")); // 1

        CartServiceImpl cart = new CartServiceImpl();

        ProductServiceImpl productDao = new ProductServiceImpl();

        ProductBean product = productDao.getProductDetails(prodId);

        int availableQty = product.getProdQuantity();

        int cartQty = cart.getProductCount(userId, prodId);

        pQty += cartQty;

        PrintWriter pw = response.getWriter();

        response.setContentType("text/html");

        if (userName == null || password == null || usertype == null || !usertype.equalsIgnoreCase("customer")) {
            Cookie[] cookies = request.getCookies();
            String cartJson = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart")) {
                        cartJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        break;
                    }
                }
            }

            // Create or update cart
            JSONObject cartJ;
            if (cartJson == null) {
                cartJ = new JSONObject();
            } else {
                cartJ = new JSONObject(cartJson);
            }
            if (pQty == 0) {
                cartJ.remove(prodId);
            } else {
                if (cartJ.has(prodId)) {
                    pQty += cartJ.getInt(prodId);
                }
                cartJ.put(prodId, pQty);
            }

            String updatedCartJson = cartJ.toString();

            Cookie cartCookie = new Cookie("cart", URLEncoder.encode(updatedCartJson, "UTF-8"));
            response.addCookie(cartCookie);
            response.sendRedirect("index.jsp");
//                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
//
//                    rd.include(request, response);
        } else {
            if (pQty == cartQty) {
                String status = cart.removeProductFromCart(userId, prodId);

                RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");

                rd.include(request, response);

                pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
            } else if (availableQty < pQty) {

                String status = null;

                if (availableQty == 0) {
                    status = "Product is Out of Stock!";
                } else {

                    cart.updateProductToCart(userId, prodId, availableQty);

                    status = "Only " + availableQty + " no of " + product.getProdName()
                            + " are available in the shop! So we are adding only " + availableQty
                            + " products into Your Cart" + "";
                }
                DemandBean demandBean = new DemandBean(userName, product.getProdId(), pQty - availableQty);

                DemandServiceImpl demand = new DemandServiceImpl();

                boolean flag = demand.addProduct(demandBean);

                if (flag) {
                    status += "<br/>Later, We Will Mail You when " + product.getProdName()
                            + " will be available into the Store!";
                }

                RequestDispatcher rd = request.getRequestDispatcher("cartDetails.jsp");

                rd.include(request, response);

                pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");

            } else {
                String status = cart.updateProductToCart(userId, prodId, pQty);

                RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");

                rd.include(request, response);

                pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}

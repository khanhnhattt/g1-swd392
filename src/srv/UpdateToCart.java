package srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import beans.DemandBean;
import beans.ProductBean;
import org.json.JSONObject;
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
            String cartJson = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart")) {
                        cartJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        break;
                    }
                }
            }
            if (cartJson != null && !cartJson.isEmpty()) {
                JSONObject cartJObject = new JSONObject(cartJson);

                if (cartJObject.has(prodId)) {
                    cartJObject.remove(prodId);
                    String updatedCartJson = cartJObject.toString();
                    Cookie updatedCartCookie = new Cookie("cart", URLEncoder.encode(updatedCartJson, "UTF-8"));
                    response.addCookie(updatedCartCookie);
                }
            }
            RequestDispatcher rd = request.getRequestDispatcher("cartDetails.jsp");
            rd.include(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}

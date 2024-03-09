package com.shashi.srv;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.shashi.beans.ProductBean;
import com.shashi.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class UpdateProductSrv
 */
@WebServlet("/UpdateProductSrv")
@MultipartConfig(maxFileSize = 16177215)
public class UpdateProductSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateProductSrv() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("usertype");
		String userName = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");

//		if (userType == null || !userType.equals("admin")) {
//
//			response.sendRedirect("login.jsp?message=Access Denied, Login As Admin!!");
//			return;
//
//		} else if (userName == null || password == null) {
//
//			response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");
//			return;
//		}

		// Login success

		String prodId = request.getParameter("pid");
		String prodName = request.getParameter("name");
		System.out.println(request.getParameter("type"));
		Integer prodType = Integer.parseInt(request.getParameter("type"));
		String prodInfo = request.getParameter("info");
		Double prodPrice = Double.parseDouble(request.getParameter("price"));
		Integer prodQuantity = Integer.parseInt(request.getParameter("quantity"));
		Part part = request.getPart("image");
		InputStream prodImage = part.getInputStream();

		ProductBean product = new ProductBean();
		product.setProdId(prodId);
		product.setProdName(prodName);
		product.setProdInfo(prodInfo);
		product.setProdPrice(prodPrice);
		product.setProdQuantity(prodQuantity);
		product.setProdCategory(prodType);
		product.setProdImage(prodImage);

		ProductServiceImpl dao = new ProductServiceImpl();
		ProductBean prevProduct = dao.getProductDetails(prodId);
		String status = dao.updateProduct(prevProduct, product);

		RequestDispatcher rd = request
				.getRequestDispatcher("addProduct.jsp?prodid=" + prodId + "&message=" + status+"&form=updateById");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

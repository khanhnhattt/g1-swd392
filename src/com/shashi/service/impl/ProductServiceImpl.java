package com.shashi.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shashi.beans.DemandBean;
import com.shashi.beans.ProductBean;
import com.shashi.dao.impl.ProductDaoImpl;
import com.shashi.service.ProductService;
import com.shashi.utility.DBUtil;
import com.shashi.utility.IDUtil;
import com.shashi.utility.MailMessage;

public class ProductServiceImpl {

	private ProductDaoImpl Dao = new ProductDaoImpl();
	private ProductDaoImpl service = new ProductDaoImpl();
	public String addProduct(String prodName, int prodType, String prodInfo, double prodPrice, int prodQuantity,
							 InputStream prodImage) {
		String status = null;
		String prodId = IDUtil.generateId();

		ProductBean product = new ProductBean(prodId, prodName, prodType, prodInfo, prodPrice, prodQuantity, prodImage, true);

		status = service.addProduct(product);

		return status;
	}


	public String addProduct(ProductBean product) {
		return Dao.addProduct(product);

	}

	public String removeProduct(String prodId) {
		return Dao.removeProduct(prodId);
	}

	public String updateProduct(ProductBean prevProduct, ProductBean updatedProduct) {
		return Dao.updateProduct(prevProduct, updatedProduct);
	}

	public String updateProductPrice(String prodId, double updatedPrice) {
		return Dao.updateProductPrice(prodId, updatedPrice);
	}

	public List<ProductBean> getAllProducts() {
		return Dao.getAllProducts();
	}

	public List<ProductBean> getAllProductsByType(String type) {
		return Dao.getAllProductsByType(type);
	}

	public List<ProductBean> searchAllProducts(String search) {
		return Dao.searchAllProducts(search);
	}


	public byte[] getImage(String prodId) {
		return Dao.getImage(prodId);
	}

	public ProductBean getProductDetails(String prodId) {
		return Dao.getProductDetails(prodId);
	}

	public String updateProductWithoutImage(String prevProductId, ProductBean updatedProduct) {
		return Dao.updateProductWithoutImage(prevProductId, updatedProduct);
	}

	public double getProductPrice(String prodId) {
		return Dao.getProductPrice(prodId);
	}


	public boolean sellNProduct(String prodId, int n) {
		return  Dao.sellNProduct(prodId,n);
	}

	public int getProductQuantity(String prodId) {
		return Dao.getProductQuantity(prodId);
	}

}

package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService
{
	@Autowired
	@Qualifier("prodDAO")
	private ProductDAO prodDAO;
	
	public ProductServiceImpl() 
	{
		prodDAO = new ProductDAO();
	}
	
	public Product addProduct(Product product) throws Exception
	{
		prodDAO.addProduct(product);
		
		return product;
	}
	
	public Product getProduct(int prodNo) throws Exception
	{
		Product prod = prodDAO.getProduct(prodNo);
		return prod;
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception
	{
		List<Product> list= prodDAO.getProductList(search);
		int totalCount = prodDAO.getProductTotal(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public int getProductTotal(Search search) throws Exception
	{
		int totalCount = prodDAO.getProductTotal(search);
		
		return totalCount;
	}
	
	public Product updateProduct(Product product) throws Exception
	{
		prodDAO.updateProduct(product);
		
		return product;
	}
}


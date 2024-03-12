package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService
{
	///필드
	@Autowired
	@Qualifier("purDAO")
	private PurchaseDAO purDAO;
	
	///생성자
	public PurchaseServiceImpl()
	{
		purDAO = new PurchaseDAO();
	}
	
	///메소드
	public Purchase addPurchase(Purchase pur) throws Exception
	{
		purDAO.addPurchase(pur);
		
		return pur;
	}
	
	public Purchase getPurchase(int tranNo) throws Exception
	{
		
		return purDAO.getPurchase(tranNo);
	}
	
	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception
	{
		List<Purchase> list= purDAO.getPurchaseList(search,userId);
		int totalCount = purDAO.getPurchaseTotal(userId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public int getPurchaseTotal(String userId) throws Exception
	{
		int totalCount = purDAO.getPurchaseTotal(userId);
		return totalCount;
	}
	
	public Map<String,Object> getSaleList(Search search) throws Exception
	{
		return null;
	}
	
	public void updatePurchase(Purchase pur) throws Exception
	{
		purDAO.updatePurchase(pur);
	}
	
	public void updateTranCode(Purchase pur) throws Exception
	{
		purDAO.updateTranCode(pur);
	}
	
	@Override
	public void decreaseStock(Purchase pur, int buyCount) throws Exception 
	{
		purDAO.decreaseStock(pur,buyCount);
	}

	@Override
	public Map<String, Object> getPurchaseProdList(int prodNo) throws Exception 
	{
		return null;
	}
}

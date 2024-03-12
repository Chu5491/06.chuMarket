package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)

//==> Meta-Data 를 다양하게 Wiring 하자...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest 
{

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	//테스트 완료!!
	public void testAddPurchase() throws Exception 
	{
		User user = new User();
		Product prod = new Product();
		Purchase pur = new Purchase();
		
		user = userService.getUser("user05");
		prod = productService.getProduct(10008);
		
		pur.setPurchaseProd(prod);
		pur.setBuyer(user);
		pur.setPaymentOption("1");
		pur.setReceiverName("추경운");
		pur.setReceiverPhone("010-5491-1508");
		pur.setDivyAddr("신림동");
		pur.setDivyRequest("빠르게!");
		pur.setTranCode("1");
		pur.setDivyDate("2024-03-06");
		pur.setTranStock(2);
		
		purchaseService.addPurchase(pur);
		
		//==> console 확인
		System.out.println(pur.getTranNo());
		
	}
	
	 //@Test
	 //테스트 완료!!
	 public void testGetPurchase() throws Exception 
 	 {
		Purchase purchase = purchaseService.getPurchase(10001);
		
		//==> console 확인
		System.out.println(purchase);
	 }
	
	 //@Test
	 //테스트 완료!!
	 public void testUpdatePurchase() throws Exception
	 {
		 
		Purchase purchase = purchaseService.getPurchase(10001);
		
		purchase.setDivyRequest("배달 짱 빠르게");
		
		System.out.println("수정할 Purchase : " + purchase);
		
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(10001);
		
		System.out.println("수정 후 Purchase : " + purchase);
		
	 }
	
	 //@Test
	 //테스트 완료!!
	 public void testGetPurchaseListAll() throws Exception
	 {
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search,"user05");
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 }
	 
	 @Test
	 //테스트 완료!!
	 public void testDecreaseStock() throws Exception
	 {
		Purchase purchase = purchaseService.getPurchase(10001);
			
		//==> console 확인
		System.out.println(purchase);
	
		purchaseService.decreaseStock(purchase, 2);
	
		purchase = purchaseService.getPurchase(10001);
			
		//==> console 확인
		System.out.println(purchase);
		
	 }
}
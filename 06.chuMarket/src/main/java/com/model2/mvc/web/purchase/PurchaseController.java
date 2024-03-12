package com.model2.mvc.web.purchase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

//==> 회원관리 Controller
@Controller
public class PurchaseController 
{
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public PurchaseController()
	{
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception 
	{
		System.out.println("/getPurchase.do");
		
		// Business logic 수행
		Purchase pur = purchaseService.getPurchase(tranNo);
		System.out.println("getPurchaseView :: " + pur);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", pur);
		modelAndView.setViewName("forward:/purchase/getPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, @SessionAttribute("user") User user) throws Exception 
	{
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() == 0 )
		{
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map = purchaseService.getPurchaseList(search,user.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		int totalCount  = purchaseService.getPurchaseTotal(user.getUserId());
		System.out.println("PurchaseList :: " + map.get("list"));
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.setViewName("forward:/purchase/listPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception 
	{
		System.out.println("/addPurchaseView.do");
		
		// Business logic 수행
		Product prod = productService.getProduct(prodNo);
		System.out.println("addPurchaseView :: " + prod);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prod", prod);
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@ModelAttribute("pur") Purchase pur,
									@RequestParam("prodNo") int prodNo,
									@RequestParam("buyerId") String buyerId) throws Exception 
	{
		System.out.println("/addPurchase.do");
		
		
		// Business logic 수행
		Product prod = productService.getProduct(prodNo);
		User user	 = userService.getUser(buyerId);
		pur.setBuyer(user);
		pur.setPurchaseProd(prod);
		
		System.out.println("addPurchase :: " + pur);
		
		purchaseService.addPurchase(pur);
		purchaseService.decreaseStock(pur, pur.getTranStock());
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", pur);
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception 
	{
		System.out.println("/updatePurchaseView.do");
		
		Purchase pur = purchaseService.getPurchase(tranNo);
		// Business logic 수행
		
		System.out.println("updatePurchaseView :: " + pur);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", pur);
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("pur") Purchase pur) throws Exception 
	{
		System.out.println("/updatePurchase.do");
		
		// Business logic 수행
		Purchase purchase = purchaseService.getPurchase(pur.getTranNo());
		int tranStock 	  = pur.getTranStock();
		int pastTranStock = purchase.getTranStock();
		
		purchaseService.updatePurchase(pur);
		
		int buyCount = 0;
		if(tranStock > pastTranStock)
		{
			buyCount = tranStock - pastTranStock;
			purchaseService.decreaseStock(pur, buyCount);
		}else
		if(tranStock < pastTranStock)
		{
			buyCount = pastTranStock - tranStock;
			purchaseService.increaseStock(purchase, buyCount);
		}
		
		System.out.println("updatePurchase :: " + pur);
		
		pur = purchaseService.getPurchase(pur.getTranNo());
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", pur);
		modelAndView.setViewName("forward:/purchase/getPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/listSale.do")
	public ModelAndView listSale(@RequestParam("prodNo") int prodNo) throws Exception 
	{
		System.out.println("/listSale.do");
		
		Map<String , Object> map = purchaseService.getSaleList(prodNo);
		// Business logic 수행
		
		System.out.println("listSale :: " + map.get("list"));
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.setViewName("forward:/purchase/listSaleView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@RequestParam("tranNo") int tranNo,
									   @RequestParam("menu") String menu) throws Exception 
	{
		System.out.println("/updateTranCode.do");
	
		
		// Business logic 수행
		Purchase pur = purchaseService.getPurchase(tranNo);
		pur.setTranCode(Integer.toString(Integer.parseInt(pur.getTranCode().trim()) + 1));
		
		purchaseService.updateTranCode(pur);
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		if(menu.equals("sale"))
		{
			modelAndView.setViewName("redirect:/listSale.do?prodNo="+pur.getPurchaseProd().getProdNo());
		}else
		{
			modelAndView.setViewName("redirect:/listPurchase.do");
		}
		
		return modelAndView;
	}
}
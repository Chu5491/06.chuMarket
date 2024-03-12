package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> ȸ������ Controller
@Controller
public class ProductController 
{
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController()
	{
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/getProduct.do")
	public ModelAndView getProduct(@RequestParam("prodNo") int prodNo,@RequestParam("menu") String menu) throws Exception 
	{
		System.out.println("/getProduct.do");
		
		Product prod = productService.getProduct(prodNo);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prod", prod);
		modelAndView.addObject("menu", menu);
		if(menu.equals("manage"))
		{
			modelAndView.setViewName("forward:/product/updateProductView.jsp");
		}else
		{
			modelAndView.setViewName("forward:/product/getProductView.jsp");
		}
		
		
		return modelAndView;
	}
	
	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct(@ModelAttribute("prod") Product prod) throws Exception 
	{
		System.out.println("/addProduct.do");
		
		prod.setManuDate(prod.getManuDate().replace("-", ""));
		
		prod = productService.addProduct(prod);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("menu", "search");
		
		modelAndView.setViewName("forward:/product/getProductView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(@ModelAttribute("prod") Product prod) throws Exception 
	{
		System.out.println("/updateProduct.do");
		
		productService.updateProduct(prod);
		
		prod = productService.getProduct(prod.getProdNo());
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("prod", prod);
		modelAndView.addObject("menu", "search");
		
		modelAndView.setViewName("forward:/product/getProductView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/listProduct.do")
	public ModelAndView listProduct(@ModelAttribute("search") Search search, @RequestParam("menu") String menu) throws Exception 
	{
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() == 0 )
		{
			search.setCurrentPage(1);
		}
		System.out.println("�˻��� : " + search.getSearchCondition() );
		
		search.setPageSize(pageSize);
		// Business logic ����
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
				
		int totalCount  = productService.getProductTotal(search);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("menu", menu);
		
		modelAndView.setViewName("forward:/product/listProductView.jsp");
		
		return modelAndView;
	}
	
}
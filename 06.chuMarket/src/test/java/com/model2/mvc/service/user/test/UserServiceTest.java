package com.model2.mvc.service.user.test;

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
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

/*
 *	FileName :  UserServiceTest.java
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
public class UserServiceTest 
{
	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	//@Test
	//테스트 완료!!
	public void testAddUser() throws Exception 
	{
		User user = new User();
		user.setUserId("chu");
		user.setUserName("chu");
		user.setPassword("1234");
		user.setSsn("1111112222222");
		user.setPhone("111-2222-3333");
		user.setAddr("경기도");
		user.setEmail("chu@chu.com");
		
		userService.addUser(user);
		
		user = userService.getUser("chu");

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals("chu", user.getUserId());
		Assert.assertEquals("chu", user.getUserName());
		Assert.assertEquals("1234", user.getPassword());
		Assert.assertEquals("111-2222-3333", user.getPhone());
		Assert.assertEquals("경기도", user.getAddr());
		Assert.assertEquals("chu@chu.com", user.getEmail());
	}
	
	//@Test
	//테스트 완료!!
	public void testGetUser() throws Exception 
	{
		
		User user = new User();
		
		user = userService.getUser("chu");

		//==> console 확인
		System.out.println(user);
		
	}
	
	 //@Test
	 //테스트 완료!!
	 public void testUpdateUser() throws Exception
	 {
		 
		User user = userService.getUser("chu");
		Assert.assertNotNull(user);
		
		Assert.assertEquals("chu", user.getUserName());
		Assert.assertEquals("111-2222-3333", user.getPhone());
		Assert.assertEquals("경기도", user.getAddr());
		Assert.assertEquals("chu@chu.com", user.getEmail());
		
		user.setUserName("chuchu");
		user.setPhone("777-7777-7777");
		user.setAddr("change");
		user.setEmail("change@change.com");
		
		userService.updateUser(user);
		
		user = userService.getUser("chu");
		Assert.assertNotNull(user);
		
		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals("chuchu", user.getUserName());
		Assert.assertEquals("777-7777-7777", user.getPhone());
		Assert.assertEquals("change", user.getAddr());
		Assert.assertEquals("change@change.com", user.getEmail());
	 }
	 
	//@Test
	//테스트 완료!! 
	public void testCheckDuplication() throws Exception
	{
		
		//==> console 확인
		System.out.println(userService.checkDuplication("chu"));
		System.out.println(userService.checkDuplication("chu"+System.currentTimeMillis()) );
	 	
		//==> API 확인
		Assert.assertFalse( userService.checkDuplication("chu") );
	 	Assert.assertTrue( userService.checkDuplication("chu"+System.currentTimeMillis()) );
		 	
	}
	
	 //==>  주석을 풀고 실행하면....
	 @Test
	 //테스트 완료!!
	 public void testGetUserListAll() throws Exception
	 {
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = userService.getUserList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = userService.getUserList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 //테스트 완료!!
	 public void testGetUserListByUserId() throws Exception
	 {
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("chu");
	 	Map<String,Object> map = userService.getUserList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = userService.getUserList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	//테스트 완료!!
	 public void testGetUserListByUserName() throws Exception
	 {
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("SCOTT");
	 	Map<String,Object> map = userService.getUserList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = userService.getUserList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}
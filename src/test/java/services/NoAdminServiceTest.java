package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.NoAdmin;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class NoAdminServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private NoAdminService noAdminService;
		
	//Supporting services --------------------
	@Autowired
	private UserAccountService userAccountService;
	
	//Tests ----------------------------------

	@Test
	public void testFindByUserAccount(){
		System.out.println("========================");
		System.out.println("== FIND BY USERACCOUNT ==");
		System.out.println("========================");
		UserAccount userAccount = userAccountService.findByUserName("nutritionist3");
		Assert.notNull(userAccount);
		NoAdmin n;
		n = noAdminService.findByUserAccount(userAccount);
		Assert.notNull(n);
		System.out.println(n.getName() + "-" + n.getSurname());
	}
	
	
	@Test
	public void testFindByPrincipal(){
		System.out.println("========================");
		System.out.println("== FIND PRINCIPAL ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		NoAdmin n;
		n = noAdminService.findByPrincipal();
		Assert.notNull(n);
		System.out.println(n.getName() + "-" + n.getSurname());
		authenticate(null);
	}
}

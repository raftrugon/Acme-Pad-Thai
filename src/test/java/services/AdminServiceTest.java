package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AdminServiceTest extends AbstractTest {

	//Service under test -------------------------
	@Autowired
	private AdminService adminService;

	//Supporting services ------------------------
	@Autowired
	private UserAccountService userAccountService;

	//Tests --------------------------------------
	@Test
	public void testFindByUserAccount() {
		System.out.println("========================");
		System.out.println("== FIND BY USERACCOUNT==");
		System.out.println("========================");
		UserAccount ua = userAccountService.findByUserName("admin1");
		Assert.notNull(ua);
		Admin a = adminService.findByUserAccount(ua);
		Assert.notNull(a);
		
		System.out.println(a.getName() + " " + a.getSurname());
	}
	
	@Test
	public void testFindByUserAccountId() {
		System.out.println("========================");
		System.out.println("== FIND BY USERACCOUNTID==");
		System.out.println("========================");
		Admin a = adminService.findByUserAccountId(3);
		Assert.notNull(a);
		
		System.out.println(a.getName() + " " + a.getSurname());
	}

	@Test
	public void testFindByPrincipal() {
		System.out.println("========================");
		System.out.println("== FIND BY PRINCIPAL ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		System.out.println(a.getName() + " " + a.getSurname());

		authenticate(null);

	}
	

}
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Cook;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ActorServiceTest extends AbstractTest {

	//Service under test -------------------------
	@Autowired
	private ActorService actorService;

	//Supporting services ------------------------
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private CookService cookService;

	//Tests --------------------------------------
	@Test
	public void testFindByUserAccount() {
		System.out.println("========================");
		System.out.println("== FIND BY USERACCOUNT==");
		System.out.println("========================");
		UserAccount ua = userAccountService.findByUserName("admin1");
		Assert.notNull(ua);
		Actor a = actorService.findByUserAccount(ua);
		Assert.notNull(a);
		
		System.out.println(a.getName() + " " + a.getSurname());

	}

	@Test
	public void testFindByPrincipal() {
		System.out.println("========================");
		System.out.println("== FIND BY PRINCIPAL ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		
		System.out.println(a.getName() + " " + a.getSurname());

		authenticate(null);

	}
	
	@Test
	public void testfindBySocialIdentity() {
		System.out.println("========================");
		System.out.println("== FIND BY SOCIALIDENTITY==");
		System.out.println("========================");
		authenticate("cook1");
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		Actor a = actorService.findBySocialIdentity(c.getSocialIdentity().getId());
		Assert.notNull(a);
		
		System.out.println(a.getName() + " " + a.getSurname());
		
		authenticate(null);
	}



}



package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Nutritionist;
import domain.SocialIdentity;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {


	//Service under test ---------------------
	@Autowired
	private SocialIdentityService socialIdentityService;
		
	//Supporting services --------------------
	@Autowired
	private NutritionistService nutritionistService;
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE SOCIALIDENTITY ==");
		System.out.println("========================");
		authenticate("sponsor1");
		SocialIdentity si = socialIdentityService.create();
		Assert.notNull(si);
		authenticate(null);
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE SOCIALIDENTITY ==");
		System.out.println("========================");
		SocialIdentity si;
		si = socialIdentityService.findOne(49);
		Assert.isTrue(si.getId()!=0);
		Assert.notNull(si);
		System.out.println(si.getId() + "-"  + si.getNick());
	}
	
	@Test
	public void testSave() {
		System.out.println("========================");
		System.out.println("== SAVE SOCIALIDENTITY ==");
		System.out.println("========================");
		authenticate("nutritionist3");
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);	
		System.out.println("SocialIdentity(BEFORE): "+ n.getSocialIdentity());
		SocialIdentity s1 = socialIdentityService.create();
		s1.setNick("raftrugon");
		s1.setSocialNetworkLink("http://www.facebook.com");
		s1.setSocialNetworkName("Facebook");
		Assert.notNull(s1);
		
		socialIdentityService.save(s1);
		n.setSocialIdentity(s1);
		System.out.println("SocialIdentity(AFTER): "+ n.getSocialIdentity());
	
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE SOCIALIDENTITY ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		SocialIdentity si = n.getSocialIdentity();
		Assert.notNull(si);
		System.out.println("(Before) Social Identity "+ si);
		socialIdentityService.delete(si);
		System.out.println("(After) Social Identity "+ n.getSocialIdentity());
		
		authenticate(null);
		
	}
		
}

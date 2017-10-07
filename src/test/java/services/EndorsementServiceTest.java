package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Endorsement;
import domain.Nutritionist;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class EndorsementServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private EndorsementService endorsementService;
	
	//Supporting services --------------------
	@Autowired
	private NutritionistService nutritionistService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE ENDORSEMENT ==");
		System.out.println("========================");
		authenticate("user3");
		Endorsement endorsement = endorsementService.create();
		Assert.notNull(endorsement);
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		
		System.out.println("========================");
		System.out.println("== FIND ONE ENDORSEMENT ==");
		System.out.println("========================");
		Endorsement e = endorsementService.findOne(188);
		Assert.notNull(e);
		System.out.println(e.getName() + "-" + e.getCurricula());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE ENDORSEMENT ==");
		System.out.println("========================");
		authenticate("user5");
		Nutritionist n = nutritionistService.findOne(174);
		Assert.notNull(n);
		Collection<Endorsement> all = n.getCurricula().getEndorsements();
		System.out.println("BEFORE: \n");
		for (Endorsement e: all) {
			System.out.println(e.getName() + "-" + e.getHomePage());
		}
		
		Endorsement e = endorsementService.create();
		e.setActor(n);
		e.setCurricula(n.getCurricula());
		
		endorsementService.save(e);
		all = n.getCurricula().getEndorsements();
		System.out.println("\nAFTER: \n");
		for (Endorsement e2: all) {
			System.out.println(e2.getName() + "-" + e2.getHomePage());
		}
		authenticate(null);
	}
	

}

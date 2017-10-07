package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Curricula;
import domain.Endorsement;
import domain.Nutritionist;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class CurriculaServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private CurriculaService curriculaService;
		
	//Supporting services --------------------
	
	@Autowired
	private NutritionistService nutritionistService;
	
	//Tests ----------------------------------
     
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE CURRICULA ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		Curricula c = curriculaService.create();
		Assert.notNull(c);
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE CURRICULA ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		Curricula c = curriculaService.findOne(167);
		Assert.notNull(c);
		System.out.println(c.getPhoto() + "-" + c.getExperienceSection());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE CURRICULA ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		
		Curricula c = curriculaService.create();
		c.setEducateSection("us");
		c.setExperienceSection("usg");
		c.setHobbiesSection("fútbol");
		c.setPhoto("http//www.foto.com");
		Collection<Endorsement> e = new HashSet<Endorsement>();
		c.setEndorsements(e);
		
		curriculaService.save(c);
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE CURRICULA ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Curricula c = n.getCurricula();
		Assert.notNull(c);
		System.out.println("CV: " + c);
		curriculaService.delete(c);
		Assert.notNull(c);
		c = n.getCurricula();
		System.out.println("CV: " + c);
		
		authenticate(null);
	}
}

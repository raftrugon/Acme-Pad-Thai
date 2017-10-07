package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Ingredient;
import domain.IngredientProperties;
import domain.Nutritionist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class IngredientPropertiesServiceTest extends AbstractTest{
	
	//Service under test ---------------------
	@Autowired
	private IngredientPropertiesService ingredientPropertiesService;
			
	//Supporting services --------------------
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private IngredientService ingredientService;
		//Tests ----------------------------------
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE INGREDIENTPROPERTIES ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		IngredientProperties ip = ingredientPropertiesService.create();
		Assert.notNull(ip);
		authenticate(null);
	}
	
	@Test
	public void testFindAll() {
		System.out.println("========================");
		System.out.println("== FIND ALL INGREDIENTPROPERTIES ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Collection<IngredientProperties> ip;
		ip = ingredientPropertiesService.findAll();
		Assert.notNull(ip);
		for (IngredientProperties ingredientProperties: ip) {
			System.out.println(ingredientProperties.getName()+ "-" + ingredientProperties.getProperties());
		}
		authenticate(null);
			
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE INGREDIENTPROPERTIES ==");
		System.out.println("========================");
		IngredientProperties ip;
		ip = ingredientPropertiesService.findOne(252);
		Assert.isTrue(ip.getId()!=0);
		Assert.notNull(ip);
		
		System.out.println(ip.getName() + "-"  + ip.getProperties());
	}

	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE INGREDIENTPROPERTIES ==");
		System.out.println("========================");
		authenticate("nutritionist1");	
		Ingredient i = ingredientService.findOne(214);
		Collection<IngredientProperties>ips = ingredientPropertiesService.findAll();
		Assert.notNull(ips);
		System.out.println("BEFORE: \n");
		for (IngredientProperties ip: ips) {
			System.out.println(ip.getName() + "-" + ip.getProperties());
		}
		IngredientProperties ip1 = ingredientPropertiesService.create();
		ip1.setIngredient(i);
		ip1.setName("cardamomo");
		ip1.setProperties("para combinados");
		ingredientPropertiesService.save(ip1);
		ips = ingredientPropertiesService.findAll();
		System.out.println("\nAFTER: \n");
		for (IngredientProperties ip: ips) {
			System.out.println(ip.getName() + "-" + ip.getProperties());
		}
		Assert.notNull(ips);
		
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE INGREDIENTPROPERTIES ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Collection<IngredientProperties> ing = ingredientPropertiesService.findAll();
		Assert.notNull(ing);
		System.out.println("BEFORE: \n");
		for(IngredientProperties ip: ing){
			System.out.println(ip.getName() + "-" + ip.getProperties());
		}
		IngredientProperties ip1 = ingredientPropertiesService.findOne(259);
		ingredientPropertiesService.delete(ip1);
		ing = ingredientPropertiesService.findAll();
		System.out.println("\nAFTER: \n");
		for(IngredientProperties ip: ing){
			System.out.println(ip.getName() + "-" + ip.getProperties());
		}
		authenticate(null);
	}
	
}

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

import domain.Ingredient;
import domain.IngredientProperties;
import domain.IngredientQuantity;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class IngredientServiceTest extends AbstractTest {

	//Service under test ---------------------
	@Autowired
	private IngredientService ingredientService;
	
	//Supporting services --------------------
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE INGREDIENT ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Ingredient i = ingredientService.create();
		Assert.notNull(i);
		authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL INGREDIENTS ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Collection<Ingredient> ingredients;
		ingredients = ingredientService.findAll();
		Assert.notNull(ingredients);
		for(Ingredient i: ingredients)
			System.out.println(i.getName() + " - " + i.getDescription() + " (" + i.getIngredientQuantities() + ")" );
		authenticate(null);
	}
	
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE INGREDIENT ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		Ingredient i = ingredientService.findOne(213);
		Assert.notNull(i);
		System.out.println(i.getName() + " - " + i.getDescription() + " (" + i.getIngredientQuantities() + ")" );
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE INGREDIENT ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Collection<Ingredient> all = ingredientService.findAll();
		System.out.println("BEFORE: \n");
		for (Ingredient i: all) {
			System.out.println(i.getName() + " - " + i.getDescription() + " (" + i.getIngredientQuantities() + ")" );
		}
		Ingredient i = ingredientService.create();
		i.setDescription("New IngredientDescription");
		i.setName("Nuevo Ingrediente");
		i.setPicture("http://www.asino/image.com");
		Collection<IngredientProperties> properties = new HashSet<IngredientProperties>();
		i.setIngredientProperties(properties);
		Collection<IngredientQuantity> quantities = new HashSet<IngredientQuantity>();
		i.setIngredientQuantities(quantities);
	
		ingredientService.save(i);
		all = ingredientService.findAll();
		Assert.notNull(all);
		
		System.out.println("\nAFTER: \n");
		for (Ingredient in: all) {
			System.out.println(in.getName() + " - " + in.getDescription() + " (" + in.getIngredientQuantities() + ")" );
		}
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE INGREDIENT ==");
		System.out.println("========================");
		authenticate("nutritionist1");
		Collection<Ingredient> ma = ingredientService.findAll();
		Assert.notNull(ma);
		Ingredient i = ingredientService.findOne(219);
		
		int num = ma.size();
		System.out.println("Number of Ingredients(BEFORE): "+ num);
		ingredientService.delete(i);
		Assert.notNull(ma);
	
		ma = ingredientService.findAll();
		int num2= ma.size();
		System.out.println("Number of Ingredients(AFTER): " +num2);
		authenticate(null);
	}
		
		
		
		
		
		
}

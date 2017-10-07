package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Recipe;
import domain.WinnerRecipe;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class WinnerRecipeServiceTest extends AbstractTest{
	
	//Service under test ---------------------
	@Autowired
	private WinnerRecipeService winnerRecipeService;
			
	//Supporting services --------------------
	@Autowired
	private RecipeService recipeService;

	//Tests ----------------------------------

	@Test
	public void testCreate() {
		System.out.println("========================");
		System.out.println("== CREATE WINNER RECIPE ==");
		System.out.println("========================");
		authenticate("admin1");
		WinnerRecipe wr = winnerRecipeService.create();
		Recipe r =recipeService.findOne(220);
		wr.setRecipe(r);
		Assert.notNull(wr);
		authenticate(null);
		
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE WINNER RECIPE ==");
		System.out.println("========================");
		WinnerRecipe wr;
		wr = winnerRecipeService.findOne(221);
		Assert.notNull(wr);
		System.out.println("WINNER RECIPE= "+ wr.getRecipe());
	}
	
	@Test
	public void testSave() {
		System.out.println("========================");
		System.out.println("== SAVE WINNER RECIPE ==");
		System.out.println("========================");
		authenticate("admin1");
		Recipe r= recipeService.findOne(220);
		Assert.notNull(r);	
		
		System.out.println("Receta antigua: "+ r.getWinnerRecipe());
		WinnerRecipe wr = winnerRecipeService.create();
		wr.setRecipe(r);
		Assert.notNull(r);
		r.setWinnerRecipe(wr);
		
		winnerRecipeService.save(wr);
		System.out.println("DESPUÉS DE GUARDAR");
		System.out.println("WinnerRecipe  tras save: "+ r.getWinnerRecipe());
	
		authenticate(null);
	}
	
	
}

package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Ingredient;
import domain.IngredientQuantity;
import domain.Measure;
import domain.Recipe;
import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class IngredientQuantityServiceTest extends AbstractTest {
	
	//Service under test ---------------------
		@Autowired
		private IngredientQuantityService ingredientQuantityService;
		
		//Supporting services --------------------
		@Autowired
		private RecipeService recipeService;
		@Autowired
		private IngredientService ingredientService;
		@Autowired
		private MeasureService measureService;
		
		//Tests ----------------------------------
		
		@Test
		public void testCreate(){
			System.out.println("========================");
			System.out.println("== CREATE INGREDIENT QUANTITY ==");
			System.out.println("========================");
			authenticate("user2");
			IngredientQuantity in = ingredientQuantityService.create();
			Assert.notNull(in);
			authenticate(null);
		}
		
		@Test
		public void testFindOne(){
			System.out.println("========================");
			System.out.println("== FIND ONE ING QUANTITY==");
			System.out.println("========================");
			authenticate("user2");
			IngredientQuantity in = ingredientQuantityService.findOne(261);
			Assert.notNull(in);
			System.out.println(in.getIngredient() + "-" + in.getMeasure());
		}
		
		@Test
		public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE ING QUANTITY ==");
		System.out.println("========================");
		authenticate("user2");
		
		Recipe r = recipeService.findOne(220);
		Assert.notNull(r);
		Ingredient ing = ingredientService.findOne(213);
		System.out.println("BEFORE: \n");
		for (IngredientQuantity i: r.getIngredientQuantities()) {
			System.out.println(i.getIngredient().getName() + "-" + i.getMeasure().getName());
		}
		
		IngredientQuantity in = ingredientQuantityService.create();
		in.setRecipe(r);
		in.setQuantity(50.2);
		in.setIngredient(ing);
		Measure m = measureService.findOne(27);
		in.setMeasure(m);
		
		ingredientQuantityService.save(in);
		
		System.out.println("\nAFTER: \n");
		for (IngredientQuantity i: r.getIngredientQuantities()) {
			System.out.println(i.getIngredient().getName() + "-" + i.getMeasure().getName());
		}
		authenticate(null);
}
		
		@Test
		public void testDelete(){
			System.out.println("========================");
			System.out.println("== DELETE QUANTITY ==");
			System.out.println("========================");
			authenticate("user2");
			
			Recipe r = recipeService.findOne(220);
			Assert.notNull(r);
			System.out.println("BEFORE: \n");
			for (IngredientQuantity i: r.getIngredientQuantities()) {
				System.out.println(i.getIngredient().getName() + "-" + i.getMeasure().getName());
			}
			IngredientQuantity in = ingredientQuantityService.findOne(261);
			ingredientQuantityService.delete(in);
			
			System.out.println("\nAFTER: \n");
			for (IngredientQuantity i: r.getIngredientQuantities()) {
				System.out.println(i.getIngredient().getName() + "-" + i.getMeasure().getName());
			}
			authenticate(null);
		}
		
		
}

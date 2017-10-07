package services;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Recipe;
import domain.Step;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class StepServiceTest extends AbstractTest {


	//Service under test ---------------------
	@Autowired
	private StepService stepService;
			
	//Supporting services --------------------
	@Autowired
	private RecipeService recipeService;

	//Tests ----------------------------------
	
	@Test
	public void testCreate() {
		System.out.println("========================");
		System.out.println("== CREATE STEP ==");
		System.out.println("========================");
		authenticate("user1");
		Recipe r =recipeService.findOne(220);
		Step step = stepService.create();
		step.setDescription("step by step");
		step.setNumberOfStep(5);
		step.setHints(new ArrayList<String>());
		step.setRecipe(r);
		r.getSteps().add(step);
		Assert.notNull(step);
		authenticate(null);
		
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE STEP ==");
		System.out.println("========================");
		Step step;
		step = stepService.findOne(223);
		Assert.notNull(step);
		System.out.println("step= "+ step);
	}
	
	@Test
	public void testSave() {
		System.out.println("========================");
		System.out.println("== SAVE STEP ==");
		System.out.println("========================");
		authenticate("user1");
		Recipe r= recipeService.findOne(220);
		Assert.notNull(r);	
		
		System.out.println("Recipes antes del guardado: "+ r.getSteps().size());
		Step s = stepService.create();
		s.setDescription("step by paso");
		s.setNumberOfStep(5);
		s.setHints(new ArrayList<String>());
		s.setRecipe(r);
		r.getSteps().add(s);
		stepService.save(s);
		System.out.println("Recipes tras guardar: "+ r.getSteps().size());
	
		authenticate(null);
	}
		
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE STEP ==");
		System.out.println("========================");
		authenticate("user1");
		Recipe r =recipeService.findOne(220);
		Assert.notNull(r);
		System.out.println("Antes del borrado: "+ r.getSteps().size());
		Step s=stepService.findOne(223);
		Assert.notNull(s);
		stepService.delete(s);
		System.out.println("Después del borrado: "+ r.getSteps().size());
		
		authenticate(null);	
	}
			
}

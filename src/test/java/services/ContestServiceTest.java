package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Contest;
import domain.Recipe;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})

@Transactional
public class ContestServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private ContestService contestService;
	
	//Supporting services --------------------
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE CONTEST ==");
		System.out.println("========================");
		authenticate("admin1");
		Contest c = contestService.create();
		Assert.notNull(c);
		authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL CONTEST ==");
		System.out.println("========================");
		Collection<Contest> ca;
		ca = contestService.findAll();
		Assert.notNull(ca);
		for(Contest c: ca)
			System.out.println(c.getTitle() + "-" + c.getWinnerRecipes());
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE CONTEST ==");
		System.out.println("========================");
		Contest c = contestService.findOne(246);
		Assert.notNull(c);
		System.out.println(c.getTitle() + "-" + c.getWinnerRecipes());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE CONTEST==");
		System.out.println("========================");
		authenticate("admin1");
		
		Collection<Contest> all = contestService.findAll();
		System.out.println("BEFORE: \n");
		for (Contest c: all) {
			System.out.println(c.getTitle() + "-" + c.getWinnerRecipes());
		}
		Contest c = contestService.create();
		c.setTitle("Concurso de platos");
		c.setOpeningTime(new Date());
		c.setClosingTime(new Date(System.currentTimeMillis()+20000));
		contestService.save(c);
		
		all = contestService.findAll();
		System.out.println("\nAFTER: \n");
		for (Contest c2: all) {
			System.out.println(c2.getTitle() + "-" + c2.getWinnerRecipes());
		}
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE CONTEST ==");
		System.out.println("========================");
		authenticate("admin2");
		Collection<Contest> all = contestService.findAll();
		System.out.println("BEFORE: \n");
		for (Contest c: all) {
			System.out.println(c.getTitle() + "-" + c.getWinnerRecipes());
		}
		Contest c2 = contestService.findOne(249);
		contestService.delete(c2);
		all = contestService.findAll();
		System.out.println("\nAFTER: \n");
		for (Contest c1: all) {
			System.out.println(c1.getTitle() + "-" + c1.getWinnerRecipes());
		}
		authenticate(null);
	}
	
	@Test
	public void testRecipeWithMoreLikes(){
		System.out.println("========================");
		System.out.println("== RECIPES WITH MORE LIKES ==");
		System.out.println("========================");
		authenticate("admin1");
		Contest c = contestService.findOne(247);
		Assert.notNull(c);
		List<Recipe> r = contestService.recipesWithMoreLikes(c.getId());
		Assert.notNull(r);
		for (Recipe re: r) {
			System.out.println(re.getTitle() + "(" + re.getLikes().size() + ")");
		}
		authenticate(null);
	}
	
	@Test
	public void testSelectWinnersRecipe(){
		System.out.println("========================");
		System.out.println("== SELECT WINNERS==");
		System.out.println("========================");
		authenticate("admin1");
		Contest c = contestService.findOne(249);
		Assert.notNull(c);
	    Collection<Recipe> res = new HashSet<Recipe>();
	    res = contestService.selectWinnerRecipes(c);
		Assert.notNull(res);
		for (Recipe re: res) {
			System.out.println(re.getTitle() + "(" + re.getLikes().size() + ")");
		}
		authenticate(null);
	}
		

}

package services;

import java.util.Collection;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Like;

import domain.Recipe;

import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class LikeServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private LikeService likeService;
	
	//Supporting services --------------------
	
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE LIKE ==");
		System.out.println("========================");
		authenticate("nutritionist2");
		Like l = likeService.create();
		Assert.notNull(l);
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){ //Creo que vale user, 19.1 enunciado
		System.out.println("========================");
		System.out.println("== FIND ONE LIKE ==");
		System.out.println("========================");
		authenticate("user3");
		Like l = likeService.findOne(177);
		Assert.notNull(l);
		System.out.println(l.getIsDislike() + "-" + l.getNoAdmin().getName());
		authenticate(null);
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE LIKE ==");
		System.out.println("========================");
		authenticate("user1");
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Recipe r = recipeService.findOne(220);
		Assert.notNull(r);
		Collection<Like> all = r.getLikes();
		System.out.println("BEFORE: \n");
		for (Like lik: all) {
			System.out.println(lik.getIsDislike() + "-" +lik.getNoAdmin().getName());
		}

		Like l = likeService.create();
		l.setIsDislike(true);
		l.setNoAdmin(u);
		
		likeService.save(l, r);
		
		System.out.println("\nAFTER: \n");
		for (Like lik: all) {
			System.out.println(lik.getIsDislike() + "-" +lik.getNoAdmin().getName());
		}
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("==DELETE LIKE ==");
		System.out.println("========================");
		authenticate("user1");
		
		Recipe r = recipeService.findOne(220);
		System.out.println("BEFORE: \n");
		for (Like li: r.getLikes()) {
			System.out.println(li.getIsDislike() + "-" +li.getNoAdmin().getName());
		}
		Like l = likeService.findOne(177);
		likeService.delete(l);
		
		System.out.println("\nAFTER: \n");
		for (Like li: r.getLikes()) {
			System.out.println(li.getIsDislike() + "-" +li.getNoAdmin().getName());
		}
		authenticate(null);
	}

}

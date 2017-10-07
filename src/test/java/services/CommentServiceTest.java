package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Comment;
import domain.Recipe;


import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CommentServiceTest extends AbstractTest{

	//Service under test ---------------------
	@Autowired
	private CommentService commentService;
			
	//Supporting services --------------------
	@Autowired
	private RecipeService recipeService;
	
	//Tests ----------------------------------
		
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE COMMENT ==");
		System.out.println("========================");
		authenticate("user2");
		Comment c = commentService.create();
		Assert.notNull(c);
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){  //No especifica si hay que loguearse para encontrar un comentario
		System.out.println("========================");
		System.out.println("== FIND ONE COMMENT ==");
		System.out.println("========================");
		Comment c;
		c = commentService.findOne(228);
		Assert.notNull(c);
		System.out.println(c.getTitle() + "-" + c.getRecipe());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE COMMENT ==");
		System.out.println("========================");
		authenticate("user1");	
		Recipe r = recipeService.findOne(220);
		Assert.notNull(r);
		Collection<Comment>all = r.getComments();
		Assert.notNull(all);
		System.out.println("BEFORE: \n");
		for (Comment c: all) {
			System.out.println(c.getTitle() + "-" + c.getNumberOfStars());
		}
		Comment c2 = commentService.create();
		c2.setNumberOfStars(2);
		c2.setRecipe(r);
		c2.setText("Un poco malo");
		c2.setTitle("#AsíNo");
		c2.setDate(new Date(System.currentTimeMillis() - 1000));
		commentService.save(c2);
		
		Collection<Comment>all2 = r.getComments();
		Assert.notNull(all2);
		System.out.println("\nAFTER: \n");
		for (Comment c3: all2) {
			System.out.println(c3.getTitle() + "-" + c3.getNumberOfStars());
		}
		authenticate(null);
	}
}

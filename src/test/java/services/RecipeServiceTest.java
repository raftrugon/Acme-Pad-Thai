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


import domain.Category;
import domain.Comment;
import domain.Contest;
import domain.Ingredient;
import domain.IngredientQuantity;
import domain.Measure;

import domain.Like;
import domain.Recipe;
import domain.Step;

import domain.User;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
public class RecipeServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private RecipeService recipeService;
	
	//Supporting services --------------------
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private IngredientQuantityService ingredientQuantityService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private MeasureService measureService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE RECIPE ==");
		System.out.println("========================");
		authenticate("user3");
		Recipe r = recipeService.create();
		Assert.notNull(r);
		authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL RECIPE==");
		System.out.println("========================");
		Collection<Recipe> recipes;
		recipes = recipeService.findAll();
		Assert.notNull(recipes);
		for(Recipe r: recipes)
			System.out.println(r.getTitle()+ "-" +r.getUser());
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE RECIPE ==");
		System.out.println("========================");
		Recipe r = recipeService.findOne(220);
		Assert.notNull(r);
		System.out.println(r.getTitle()+ "-" +r.getUser());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE RECIPE ==");
		System.out.println("========================");
		authenticate("user1");
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Collection<Recipe> all = recipeService.findAll();
		System.out.println("BEFORE: \n");
		for (Recipe r: all) {
			System.out.println(r.getTitle()+ "-" +r.getUser().getName());
		}
		
		Recipe re = recipeService.create();
		Date date = new Date(100000000L);
		re.setAuthorDate(date);
		Collection<Category> cat = new HashSet<Category>();
		re.setCategories(cat);
		Collection<Comment> com = new HashSet<Comment>();
		re.setComments(com);
		Collection<IngredientQuantity> col = new HashSet<IngredientQuantity>();
		re.setIngredientQuantities(col);
		Collection<Like> lik = new HashSet<Like>();
		re.setLikes(lik);
		re.setReadOnly(false);
		Collection<Step> st = new HashSet<Step>();
		re.setSteps(st);
		re.setSummary("New Recipe Summary");
		re.setTicker("777733-IJDK");
		re.setTitle("Original Recipe Title");
		re.setUser(u);
		
		IngredientQuantity in = ingredientQuantityService.create();
		Measure m = new Measure();
		m.setIsInteger(false);
		m.setName("POUNDS");
		in.setMeasure(m);
		in.setQuantity(3.0);
		Ingredient ingredient = ingredientService.findOne(213);
		in.setIngredient(ingredient);
		in.setRecipe(re);
		re.getIngredientQuantities().add(in);
		
		Category c = categoryService.findOne(42);
		re.getCategories().add(c);
		
		recipeService.save(re);
	
		all = recipeService.findAll();
		
		System.out.println("\nAFTER: \n");
		for (Recipe r: all) {
			System.out.println(r.getTitle()+ "-" +r.getUser().getName());
		}
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE RECIPE ==");
		System.out.println("========================");
		authenticate("user3");
		
		Recipe r = recipeService.create();
		Assert.notNull(r);
		r.setTicker("022323-AFAF");
		r.setTitle("Example Title");
		r.setSummary("Original Summary");
		Date d1 = new Date(System.currentTimeMillis() - 1000);
		r.setAuthorDate(d1);
		Date d2 = new Date(System.currentTimeMillis() - 500);
		r.setLastUpdate(d2);
		r.setReadOnly(false);
		
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		r.setUser(u);
		
		Category c = categoryService.findOne(44);
		Assert.notNull(c);
		r.getCategories().add(c);
		
		IngredientQuantity in = ingredientQuantityService.create();
		Assert.notNull(in);
		Measure m = measureService.findOne(29);
		in.setMeasure(m);
		in.setQuantity(3.0);
		Ingredient ingredient = ingredientService.findOne(215);
		Assert.notNull(ingredient);
		in.setIngredient(ingredient);
		in.setRecipe(r);
		r.getIngredientQuantities().add(in);
	
		recipeService.save(r);
		
		List<Recipe> recetas = (List<Recipe>) recipeService.findAllRecipesByKeyword("Example Title");
		Assert.notNull(recetas);
		Recipe aBorrar = recetas.get(0);
		Collection<Recipe> res = recipeService.findAll();
		Assert.notNull(res);
		System.out.println("Número recetas (ANTES): "+ res.size());
		
		recipeService.delete(aBorrar);
		
		res = recipeService.findAll();
		System.out.println("Número recetas (DESPUÉS): "+ res.size());
		
		authenticate(null);
	}
	
	@Test
	public void testShowRecipesByCategory(){
		System.out.println("========================");
		System.out.println("== SHOW RECIPES BY CATEGORY ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<Recipe> res;
		res= recipeService.showRecipesByCategory(32);
		Assert.notNull(res);
		for(Recipe r2: res) {
			System.out.println(r2.getTitle()+ "-" +r2.getUser());
		}
		authenticate(null);
	}
	
	@Test
	public void testShowRecipesByNestedCategories(){
		System.out.println("========================");
		System.out.println("== SHOW RECIPES BY NESTED CATEGORY ==");
		System.out.println("========================");
		authenticate("admin1");
		Category c = categoryService.findOne(40);
		Collection<Recipe> res = new HashSet<Recipe>();
		Collection<Category> categories = categoryService.nestedCategories(c);
		
		for(Category c2: categories) {
			res = recipeService.showRecipesByCategory(c2.getId());
		}
		for(Recipe r:res){
			System.out.println(r.getTitle()+ "-" +r.getUser());
		}
		authenticate(null);
	}
	
	@Test
	public void testFindAllRecipesByKeyword(){
		System.out.println("========================");
		System.out.println("== FIND ALL RECIPES ==");
		System.out.println("========================");
		Collection<Recipe> res;
		res = recipeService.findAllRecipesByKeyword("pollo");
		for (Recipe r:res){
			System.out.println(r.getTitle()+ "-" +r.getUser());
		}
	}
	
	@Test
	public void testShowNoReadOnlyRecipes(){
		System.out.println("========================");
		System.out.println("== SHOW ==");
		System.out.println("========================");
		Collection<Recipe> res;
		res = recipeService.showNoReadOnlyRecipes();
		for (Recipe r:res){
			System.out.println(r.getTitle()+ "-" +r.getUser());
		}
	}
	
	@Test
	public void testShowLastRecipesCreated(){
	System.out.println("========================");
	System.out.println("== SHOW LAST RECIPES ==");
	System.out.println("========================");
	List<Recipe> res;
	res = recipeService.showLastRecipesCreated();
	for (Recipe r:res){
		System.out.println(r.getTitle()+ "-" +r.getUser());
	}
	}
	
	@Test
	public void testShowLastRecipesByPeopleWhoYouFollow(){
		System.out.println("========================");
		System.out.println("== SHOW LAST RECIPES ==");
		System.out.println("========================");
		authenticate("user1");
	    Collection<Recipe> res;
		res = recipeService.showLastRecipesByPeopleWhoYouFollow();
		for (Recipe r:res){
			System.out.println(r.getTitle()+ "-" +r.getUser());
		}
		authenticate(null);
	}
	
	@Test
	public void testShowRecipesWithFiveOrMoreLikesAndZeroDislikes(){
		System.out.println("========================");
		System.out.println("== SHOW RECIPES ==");
		System.out.println("========================");
		authenticate("user1");
		User u = userService.findByPrincipal();
		Assert.notNull(u);
	    Collection<Recipe> res;
		res = recipeService.showRecipesWithFiveOrMoreLikesAndZeroDislikes(u.getId());
		for (Recipe r:res){
			System.out.println(r.getTitle()+ "-" +r.getLikes());
		}
	}
	
	@Test
	public void testLovedRecipes(){
		System.out.println("========================");
		System.out.println("== Loved Recipes ==");
		System.out.println("========================");
		authenticate("user2");
		User u = userService.findByPrincipal();
		Assert.notNull(u);
	    Collection<Recipe> res = recipeService.lovedRecipes(u.getId());
	    for(Recipe r: res)
	    	System.out.println(r.getTitle()+ "-" +r.getLikes());
		authenticate(null);
		}

	@Test
	public void testHatedRecipes(){
		System.out.println("========================");
		System.out.println("== Hated Recipes ==");
		System.out.println("========================");
		authenticate("user3");
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Collection<Recipe> res = recipeService.hatedRecipes(u.getId());
	    for(Recipe r: res)
	    	System.out.println(r.getTitle()+ "-" +r.getLikes());
		authenticate(null);
	}
	
	@Test
	public void testRegisterInAContest(){
		System.out.println("========================");
		System.out.println("== Register in contest ==");
		System.out.println("========================");
		authenticate("user1");
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Recipe r = recipeService.findOne(220);
		Assert.notNull(r);
		
		Contest c = contestService.findOne(248);
		Collection<Recipe> res = new HashSet<Recipe>();
		c.setRecipes(res);
		Assert.notNull(c);
		recipeService.registerInAContest(r, c);
		for(Recipe rec: c.getRecipes())
			System.out.println(rec.getTitle());
		authenticate(null);
	}
	
	@Test
	public void testfindRecipeByLike(){
		System.out.println("========================");
		System.out.println("== FIND RECIPE BY LIKE ==");
		System.out.println("========================");
		authenticate("user3");
	    Recipe res = recipeService.findRecipeByLike(176);
	    Assert.notNull(res);
		System.out.println("Receta: "+res);
		authenticate(null);
	}
		

}

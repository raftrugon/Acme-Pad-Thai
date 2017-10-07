package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RecipeRepository;
import domain.Category;
import domain.Comment;
import domain.Contest;
import domain.IngredientQuantity;
import domain.Like;
import domain.NoAdmin;
import domain.Recipe;
import domain.Step;
import domain.User;

@Service
@Transactional
public class RecipeService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecipeRepository recipeRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private NoAdminService noAdminService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private IngredientQuantityService ingredientQuantityService;
	@Autowired
	private StepService stepService;
	@Autowired
	private CommentService commentService;

	
	// Constructors -----------------------------------------------------------
	public RecipeService() {
		super();
	}	
	
	// Simple CRUD methods ----------------------------------------------------
	
	//19.1
	public Recipe create() {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		
		Recipe res = new Recipe();
		String ticker = checkTicker(cadenaNumerica() + "-"
				+ cadenaAlfabetica());

		
		Date date = new Date(System.currentTimeMillis() - 1000);	
		
		Collection<Step> steps = new ArrayList<Step>();
		Collection<Category> categories = new ArrayList<Category>();
		Collection<Comment> comments = new ArrayList<Comment>();
		Collection<IngredientQuantity> ingredientQuantity = new ArrayList<IngredientQuantity>();
		Collection<Like> likes = new ArrayList<Like>();
		Collection<String> pictures = new ArrayList<String>();
		
		res.setTicker(ticker);
		res.setAuthorDate(date);
		res.setLastUpdate(date);
		res.setSteps(steps);
		res.setCategories(categories);
		res.setComments(comments);
		res.setIngredientQuantities(ingredientQuantity);
		res.setLikes(likes);
		res.setPictures(pictures);
		res.setReadOnly(false);
		res.setUser(u);
				
		return res;
	}

	//Método privado para el ticker
	private String cadenaNumerica() {
		String res = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; //Months start at 0
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String yearString = String.valueOf(year);
		String yy = yearString.substring(2);
		String mm;
		if(month>9)
			mm = String.valueOf(month);
		else
			mm = "0"+String.valueOf(month);
		String dd;
		if(day>9)
			dd = String.valueOf(day);
		else
			dd = "0"+String.valueOf(day);
		
		res = yy+mm+dd;

		return res;
	}

	private String cadenaAlfabetica() {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String cadena = "";
		for (int i = 0; i < 4; i++) {
			int num = (int) (Math.random() * 26);
			cadena = cadena.concat(caracteres.substring(num, num + 1));
		}
		return cadena;

	}
	
	private String checkTicker(String ticker){
		
		Collection<Recipe> rec = new ArrayList<Recipe>(recipeRepository.showNoReadOnlyRecipes());
		for(Recipe r: rec){
			if(r.getTicker().equals(ticker)){
				ticker = cadenaNumerica() + "-"
						+ cadenaAlfabetica();
				checkTicker(ticker);
			}
		}
		return ticker;
	}

	public Collection<Recipe> findAll() {
		Collection<Recipe> res;
		res=recipeRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	
	public Recipe findOne(int recipeId) {
		Assert.isTrue(recipeId != 0);		
		Recipe res = recipeRepository.findOne(recipeId);
		Assert.notNull(res);
		return res;
	}
	
	public Recipe save(Recipe recipe) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(recipe);
		Date date = new Date(System.currentTimeMillis() - 1000);	
		recipe.setLastUpdate(date);
		
		Assert.notNull(recipe);
		recipeRepository.save(recipe);
		
		return recipe;
	}
	
	public void delete(Recipe recipe) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(recipe);
		Assert.isTrue(recipe.getId() != 0);
		Assert.isTrue(recipeRepository.exists(recipe.getId()));
		
		recipeRepository.delete(recipe.getId());
	}

	// Other business methods -------------------------------------------------
	
	public Double[] avgStddevNumberOfSteps(){
		Double[] res = null;
		res = recipeRepository.avgStddevNumberOfSteps();
		Assert.notNull(res);
		for(int i=0;i<res.length;i++){
			if(res[i]==null)
				res[i]=0.0;
		}
		return res;
	}
	
	public Double[] avgStddevNumberOfIngredients(){
		Double[] res = null;
		res = recipeRepository.avgStddevNumberOfIngredients();
		Assert.notNull(res);
		for(int i=0;i<res.length;i++){
			if(res[i]==null)
				res[i]=0.0;
		}
		return res;
	}
	
	//17.2	Mostrar las recetas por categoría
	public Collection<Recipe> showRecipesByCategory(int categoryId) {
		Assert.notNull(categoryId);
		Collection<Recipe> res;
		res=recipeRepository.showRecipesByCategory(categoryId);
		Assert.notNull(res);
		return res;
	}
	
	public Collection<Recipe> showRecipesByNestedCategories(Category category) {
		Assert.notNull(category);
		Collection<Recipe> res = new ArrayList<Recipe>();
		Set<Category> categories =  new HashSet<Category>(categoryService.nestedCategories(category));
		for(Category c: categories){
			Collection<Recipe> recipes = showRecipesByCategory(c.getId());
			for(Recipe r: recipes){
				if(!res.contains(r))
					res.add(r);
			}
		}
		
		return res;
	}
	
	//17.5 	Buscar por palabra en ticker, sumario o título
	public Collection<Recipe> findAllRecipesByKeyword(String keyword) {
		Collection<Recipe> res= new ArrayList<Recipe>();
		res=recipeRepository.findAllRecipesByKeyword(keyword);
		return res;		
	}
	
	//Descartar copias de recetas participantes en concursos
	public Collection<Recipe> showNoReadOnlyRecipes(){
		Collection<Recipe> res= new ArrayList<Recipe>();
		res=recipeRepository.showNoReadOnlyRecipes();
		return res;		
	}
	
	//Descartar copias de recetas participantes en concursos (un solo usuario)
	public Collection<Recipe> showNoReadOnlyRecipes(int userId){
		Collection<Recipe> res= new ArrayList<Recipe>();
		res=recipeRepository.showNoReadOnlyRecipes(userId);
		return res;		
	}
	
	public List<Recipe> showLastRecipesCreated() {
		List<Recipe> res= new ArrayList<Recipe>();
		res=recipeRepository.showLastRecipesCreated();
		Assert.notNull(res);
		return res;	
	}
	
	public Collection<Recipe> showLastRecipesByPeopleWhoYouFollow(){
		NoAdmin na = noAdminService.findByPrincipal();
		Assert.notNull(na);
		List<Recipe> lastRecipes = showLastRecipesCreated();
		Assert.notNull(lastRecipes);
		List<Recipe> res = new ArrayList<Recipe>();
		for(Recipe r: lastRecipes){
			if(na.getNoAdmins().contains(r.getUser())){
				res.add(r);
				if(res.size()>=5) //Number of recipes that will be displayed.
					break;
			}
				
		}
		
		return res;
	}

	//19.2 Recetas con más de 5 likes y 0 dislikes
	public Collection<Recipe> showRecipesWithFiveOrMoreLikesAndZeroDislikes(int userId) {
		Collection<Recipe> res;
		res=recipeRepository.showRecipesWithFiveOrMoreLikesAndZeroDislikes(userId);
		return res;
		
	}
	
	public Collection<Recipe> lovedRecipes(int noAdminId){
		Collection<Recipe> res;
		res=recipeRepository.lovedRecipes(noAdminId);
		Assert.notNull(res);
	
		return res;
	}
	
	public Collection<Recipe> hatedRecipes(int noAdminId){
		Collection<Recipe> res;
		res=recipeRepository.hatedRecipes(noAdminId);
		Assert.notNull(res);
	
		return res;
	}
	
	public Integer numberOfLikes(Recipe recipe){
		Integer res;
		res = recipeRepository.numberOfLikes(recipe.getId());
		Assert.notNull(res);
	
		return res;
	}
	
	public Integer numberOfDislikes(Recipe recipe){
		Integer res;
		res = recipeRepository.numberOfDislikes(recipe.getId());
		Assert.notNull(res);
	
		return res;
	}
	
	public void registerInAContest(Recipe r, Contest contest){
		User u;
		u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.isTrue(r.getUser()==u);//El usuario es el dueño de la receta.
		Assert.isTrue(numberOfLikes(r)>=5);
		Assert.isTrue(numberOfDislikes(r)==0);
		Assert.notNull(contest);
		Assert.isTrue(contest.getId()!=0);
		Date actualDate = new Date();
		Assert.isTrue(actualDate.after(contest.getOpeningTime()) 
				&& actualDate.before(contest.getClosingTime()));
		Recipe copy;
		copy = create();
		copy.setTicker(r.getTicker());
		copy.setTitle(r.getTitle());
		copy.setSummary(r.getSummary());
		copy.setAuthorDate(r.getAuthorDate());
		copy.setLastUpdate(r.getLastUpdate());
		copy.getPictures().addAll(r.getPictures());
		copy.setReadOnly(true);
		copy.setUser(r.getUser());

		copy.getCategories().addAll(r.getCategories());

		Recipe newCopy;
		newCopy = recipeRepository.save(copy);	
		newCopy.getUser().getRecipes().add(newCopy);
		for(Category c: r.getCategories())
			c.getRecipes().add(newCopy);
		contest.getRecipes().add(newCopy);
		
		for(Comment c: r.getComments()){
			Comment newComment = commentService.create();
			newComment.setTitle(c.getTitle());
			newComment.setText(c.getText());
			newComment.setNumberOfStars(c.getNumberOfStars());
			newComment.setDate(c.getDate());
			newComment.setRecipe(newCopy);
			commentService.save(newComment);			
		}
		
		for(Step s: r.getSteps()){
			Step newStep = stepService.create();
			newStep.setDescription(s.getDescription());
			newStep.setPicture(s.getPicture());
			newStep.getHints().addAll(s.getHints());
			newStep.setNumberOfStep(s.getNumberOfStep());
			newStep.setRecipe(newCopy);
			stepService.save(newStep);
		}
		
		for(Like l: r.getLikes()){
			Like newLike = likeService.create();
			newLike.setIsDislike(l.getIsDislike());
			likeService.save(newLike, newCopy);
		}
		
		for(IngredientQuantity i: r.getIngredientQuantities()){
			IngredientQuantity newIngredientQuantity = ingredientQuantityService.create();
			newIngredientQuantity.setMeasure(i.getMeasure());
			newIngredientQuantity.setQuantity(i.getQuantity());
			newIngredientQuantity.setIngredient(i.getIngredient());
			newIngredientQuantity.setRecipe(newCopy);
			ingredientQuantityService.save(newIngredientQuantity);
		}

	}
	
	public Recipe findRecipeByLike(int likeId){
		Recipe res;
		res=recipeRepository.findRecipeByLike(likeId);
		Assert.notNull(res);
		return res;
	}
}

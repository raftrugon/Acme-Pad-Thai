package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.BannerService;
import services.BillService;
import services.CategoryService;
import services.ContestService;
import services.LikeService;
import services.NoAdminService;
import services.RecipeService;
import services.UserService;
import domain.Actor;
import domain.Banner;
import domain.Category;
import domain.Contest;
import domain.Like;
import domain.NoAdmin;
import domain.Nutritionist;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/recipe")
public class RecipeController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private UserService userService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private BillService billService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private NoAdminService noAdminService;
	@Autowired
	private ContestService contestService;
	
	//Constructor ---------------------------
	public RecipeController() {
		super();
	}
	
	//Listing -------------------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list() {
			Boolean noAdmin = false;
			
			try{
				Actor a = actorService.findByPrincipal();
				if(a instanceof User || a instanceof Nutritionist)
					noAdmin = true;
				}
			catch(Throwable oops){
			}		
			
			ModelAndView result;
			Collection<Recipe> recipes;
			Collection<Category> categories;

			recipes = recipeService.showNoReadOnlyRecipes();
			categories = categoryService.findAll();	
			
			Collection<Banner> banners;
			banners = bannerService.findByActiveCampaigns();
			Assert.notNull(banners);
			Banner banner = null;
			if(!banners.isEmpty()){
				banner = bannerService.showRandomBanner(banners);
				Assert.notNull(banner);
				billService.updateBill(banner);
			}
			
			result = new ModelAndView("recipe/list");
			result.addObject("noAdmin", noAdmin);
			result.addObject("banner", banner);
			result.addObject("recipes", recipes);
			result.addObject("categories", categories);
			return result;	
		}
		
		@RequestMapping(value="/search", method = RequestMethod.POST)
		public ModelAndView listByCategory(@RequestParam("keyword") String keyword) {
			Boolean noAdmin = false;
			ModelAndView result;
			Collection<Recipe> recipes; 
			Collection<Recipe> search;
			Collection<Category> categories;
			
			recipes = recipeService.showNoReadOnlyRecipes();
			search = recipeService.findAllRecipesByKeyword(keyword);
			categories = categoryService.findAll();
			
			try{
				Actor a = actorService.findByPrincipal();
				if(a instanceof User || a instanceof Nutritionist)
					noAdmin = true;
				}
			catch(Throwable oops){
			}
			
			result = new ModelAndView("recipe/list");
			
			result.addObject("recipes", recipes);
			result.addObject("noAdmin", noAdmin);
			result.addObject("categories", categories);
			result.addObject("search", search);
			result.addObject("keyword", keyword);
			return result;
		}
		
		@RequestMapping(value="/group", method = RequestMethod.POST)
		public ModelAndView groupByCategory(@RequestParam int categoryId) {
			ModelAndView result;
			Boolean noAdmin = false;
			Collection<Recipe> recipes;
			Collection<Category> categories;
			Category category;
			if(categoryId!=0){
				category = categoryService.findOne(categoryId);
				recipes = recipeService.showRecipesByNestedCategories(category);
			}
			else
				recipes = recipeService.showNoReadOnlyRecipes();
			
			try{
				Actor a = actorService.findByPrincipal();
				if(a instanceof User || a instanceof Nutritionist)
					noAdmin = true;
			}
			catch(Throwable oops){
			}

			categories = categoryService.findAll();
			result = new ModelAndView("recipe/list");
			result.addObject("noAdmin", noAdmin);
			result.addObject("recipes", recipes);
			result.addObject("categories", categories);
			return result;
		}
		
		@RequestMapping(value="/user/list", method = RequestMethod.GET)
		public ModelAndView listMyRecipes() {
			ModelAndView result;
			Boolean noAdmin = false;
			Collection<Recipe> recipes;
			User u;
			u = userService.findByPrincipal();
			Assert.notNull(u);
			recipes = recipeService.showNoReadOnlyRecipes(u.getId());
			Assert.notNull(recipes);
			
			Actor a = actorService.findByPrincipal();
			if(a instanceof User || a instanceof Nutritionist)
				noAdmin = true;
			
			Collection<Banner> banners;
			banners = bannerService.findByActiveCampaigns();
			Assert.notNull(banners);
			Banner banner = null;
			if(!banners.isEmpty()){
				banner = bannerService.showRandomBanner(banners);
				Assert.notNull(banner);
				billService.updateBill(banner);
			}			
				
			result = new ModelAndView("recipe/user/list");
			result.addObject("noAdmin", noAdmin);
			result.addObject("recipes", recipes);
			result.addObject("banner", banner);

			return result;	
		}
		
		@RequestMapping(value="/userList", method = RequestMethod.GET)
		public ModelAndView listUserRecipes(@RequestParam int userId) {
			ModelAndView result;
			Boolean noAdmin = false;
			Collection<Recipe> recipes;
			
			recipes = recipeService.showNoReadOnlyRecipes(userId);
			Assert.notNull(recipes);
			
			try{
				Actor a = actorService.findByPrincipal();
				if(a instanceof User || a instanceof Nutritionist)
					noAdmin = true;
			}
			catch(Throwable oops){
				
			}
			
			Collection<Banner> banners;
			banners = bannerService.findByActiveCampaigns();
			Assert.notNull(banners);
			Banner banner = null;
			if(!banners.isEmpty()){
				banner = bannerService.showRandomBanner(banners);
				Assert.notNull(banner);
				billService.updateBill(banner);
			}			
				
			result = new ModelAndView("recipe/userList");
			result.addObject("noAdmin", noAdmin);
			result.addObject("recipes", recipes);
			result.addObject("banner", banner);

			return result;	
		}
		
		@RequestMapping(value="/contestList", method = RequestMethod.GET)
		public ModelAndView listContestRecipes(@RequestParam int contestId) {
			ModelAndView result;
			Boolean noAdmin = false;
			Collection<Recipe> recipes;
			Contest contest;
			
			contest = contestService.findOne(contestId);
			recipes = contest.getRecipes();
			Assert.notNull(recipes);
			
			try{
				Actor a = actorService.findByPrincipal();
				if(a instanceof User || a instanceof Nutritionist)
					noAdmin = true;
			}
			catch(Throwable oops){
				
			}
			
			Collection<Banner> banners;
			banners = bannerService.findByActiveCampaigns();
			Assert.notNull(banners);
			Banner banner = null;
			if(!banners.isEmpty()){
				banner = bannerService.showRandomBanner(banners);
				Assert.notNull(banner);
				billService.updateBill(banner);
			}			
				
			result = new ModelAndView("recipe/contestList");
			result.addObject("noAdmin", noAdmin);
			result.addObject("recipes", recipes);
			result.addObject("banner", banner);

			return result;	
		}
		
		@RequestMapping(value="/recommendedRecipes/list", method = RequestMethod.GET)
		public ModelAndView listRecommendedRecipes() {
			ModelAndView result;
			Collection<Recipe> recipes;
			Boolean noAdmin = false;
			
			Actor a = actorService.findByPrincipal();
			if(a instanceof User || a instanceof Nutritionist)
				noAdmin = true;
			
			recipes = recipeService.showLastRecipesByPeopleWhoYouFollow();
			Assert.notNull(recipes);
			
			Collection<Banner> banners;
			banners = bannerService.findByActiveCampaigns();
			Assert.notNull(banners);
			Banner banner = null;
			if(!banners.isEmpty()){
				banner = bannerService.showRandomBanner(banners);
				Assert.notNull(banner);
				billService.updateBill(banner);
			}			
				
			result = new ModelAndView("recipe/recommendedRecipes/list");
			result.addObject("noAdmin", noAdmin);
			result.addObject("recipes", recipes);
			result.addObject("banner", banner);

			return result;	
		}
		
		//Creating ------------------------------------
		@RequestMapping(value="/user/create", method = RequestMethod.GET)
		public ModelAndView create(){
			User u = userService.findByPrincipal();
			ModelAndView result;
			Recipe recipe;
			
			recipe = recipeService.create();
			recipe.setUser(u);
			result= createEditModelAndView(recipe);
			result.addObject("owner", true);
			return result;
		}
				
		
		
		//Edition -------------------------------------
		@RequestMapping(value="/user/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int recipeId) {
			Boolean owner = false;
			
			ModelAndView result;
			Recipe recipe;
			
			recipe = recipeService.findOne(recipeId);
			
			User u = userService.findByPrincipal();		
			if(recipe.getUser().equals(u))
				owner=true;
			
			Assert.notNull(recipe);
			result = createEditModelAndView(recipe);
			result.addObject("owner",owner);
			return result;
		}
		
		@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Recipe recipe, BindingResult binding,RedirectAttributes redirectAttrs) {
			ModelAndView result;
			if (binding.hasErrors()) {
				result = createEditModelAndView(recipe);
//				for(ObjectError o: binding.getAllErrors()){
//					System.out.println("ARGUMENT: "+o.getArguments());
//					System.out.println("CODE: "+o.getCode());
//					System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
			} else {
				try {
					recipeService.save(recipe);
					redirectAttrs.addFlashAttribute("message", "recipe.commit.ok");
					redirectAttrs.addFlashAttribute("msgType", "success");
					result= new ModelAndView("redirect:/recipe/list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(recipe, "recipe.commit.error");
				}
			}
			return result;
			
		}
		
		@RequestMapping(value="/user/edit", method= RequestMethod.POST, params="delete")
		public ModelAndView delete(Recipe recipe, BindingResult binding){
			ModelAndView result;
		
			try{
				recipeService.delete(recipe);
				result= new ModelAndView("redirect:/recipe/list.do");
			}catch (Throwable oops){
				result= createEditModelAndView(recipe, "recipe.commit.error");
			}
			return result;
		}
		
		@RequestMapping(value="/like", method= RequestMethod.GET)
		public ModelAndView like(@RequestParam int recipeId, RedirectAttributes redirectAttrs){
			ModelAndView result;
		
			try {
				Recipe r = recipeService.findOne(recipeId);
				Assert.isTrue(!r.getReadOnly());
				
				Actor a = actorService.findByPrincipal();
				Assert.isTrue(!r.getUser().equals(a));
				
				NoAdmin na = noAdminService.findByPrincipal();
				Assert.notNull(na);
				Like l = likeService.create();
				l.setIsDislike(false);
				l.setNoAdmin(na);
				likeService.save(l, r);

				redirectAttrs.addFlashAttribute("message", "recipe.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {
//				System.out.println(oops.getLocalizedMessage());
//				System.out.println(oops.getMessage());
				redirectAttrs.addFlashAttribute("message", "recipe.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}
			
			result= new ModelAndView("redirect:/recipe/list.do");
			
			return result;
		}
		
		@RequestMapping(value="/dislike", method= RequestMethod.GET)
		public ModelAndView dislike(@RequestParam int recipeId, RedirectAttributes redirectAttrs){
			ModelAndView result;
		
			try {
				Recipe r = recipeService.findOne(recipeId);
				Assert.isTrue(!r.getReadOnly());
				
				Actor a = actorService.findByPrincipal();
				Assert.isTrue(!r.getUser().equals(a));
				
				NoAdmin na = noAdminService.findByPrincipal();
				Assert.notNull(na);
				Like l = likeService.create();
				l.setIsDislike(true);
				l.setNoAdmin(na);
				likeService.save(l, r);

				redirectAttrs.addFlashAttribute("message", "recipe.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {
//				System.out.println(oops.getLocalizedMessage());
//				System.out.println(oops.getMessage());
				redirectAttrs.addFlashAttribute("message", "recipe.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}
			
			result= new ModelAndView("redirect:/recipe/list.do");
			
			return result;
		}
		
		@RequestMapping(value="/noLike", method= RequestMethod.GET)
		public ModelAndView noLike(@RequestParam int likeId, RedirectAttributes redirectAttrs){
			ModelAndView result;
		
			try {
				Recipe r = recipeService.findRecipeByLike(likeId);
				Assert.isTrue(!r.getReadOnly());
				
				Actor a = actorService.findByPrincipal();
				Assert.isTrue(!r.getUser().equals(a));
				
				
				Like l = likeService.findOne(likeId);
				Assert.notNull(noAdminService.findByPrincipal());
				likeService.delete(l);

				redirectAttrs.addFlashAttribute("message", "recipe.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {

				redirectAttrs.addFlashAttribute("message", "recipe.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}
			
			result= new ModelAndView("redirect:/recipe/list.do");
			
			return result;
		}
		
		//Ancillary methods -----------------------------
		
		protected ModelAndView createEditModelAndView(Recipe recipe) {
			ModelAndView result;
			result = createEditModelAndView(recipe, null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Recipe recipe, String message) {
			ModelAndView result;
			
			Collection<Category> categories;
			categories = categoryService.findAll();
			
			result = new ModelAndView("recipe/user/edit");
			result.addObject("categories", categories);
			result.addObject("recipe", recipe);
			result.addObject("message", message);
			

			return result;
		}

}

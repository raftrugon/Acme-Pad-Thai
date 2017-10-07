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

import services.IngredientPropertiesService;
import services.IngredientQuantityService;
import services.IngredientService;
import services.MeasureService;
import services.RecipeService;
import services.UserService;
import domain.Ingredient;
import domain.IngredientProperties;
import domain.IngredientQuantity;
import domain.Measure;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/ingredient")
public class IngredientController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;
	@Autowired
	private IngredientQuantityService ingredientQuantityService;
	@Autowired
	private MeasureService measureService;
	@Autowired
	private IngredientPropertiesService ingredientPropertiesService;
	
	//Constructor ---------------------------
	public IngredientController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int recipeId) {			
		ModelAndView result;
		Recipe r;
		Collection<IngredientQuantity> ingredientQuantities;
		Boolean owner = false;
		
		r = recipeService.findOne(recipeId);
		ingredientQuantities = r.getIngredientQuantities();
		
		try{
			User u = userService.findByPrincipal();		
			if(r.getUser().equals(u))
				owner=true;
		}
		catch(Throwable oops){
			
		}

		
		result = new ModelAndView("ingredient/list");
		result.addObject("owner", owner);
		result.addObject("ingredientQuantities", ingredientQuantities);
		result.addObject("recipe", r);
		return result;
	}
	
	@RequestMapping(value="/nutritionist/list", method = RequestMethod.GET)
	public ModelAndView listMyIngredients() {			
		ModelAndView result;
		Collection<Ingredient> ingredients;
		
		ingredients = ingredientService.findAll();
		
		result = new ModelAndView("ingredient/nutritionist/list");
		result.addObject("ingredients", ingredients);
		return result;	
	}
	
	@RequestMapping(value="/nutritionist/listProperties", method = RequestMethod.GET)
	public ModelAndView listProperties() {			
		ModelAndView result;
		Collection<IngredientProperties> ingredientProperties;
		
		ingredientProperties = ingredientPropertiesService.findAll();
		Assert.notNull(ingredientProperties);
		
		result = new ModelAndView("ingredient/nutritionist/listProperties");
		result.addObject("ingredientProperties", ingredientProperties);
		return result;
	}
		
	//Creating ------------------------------------
	@RequestMapping(value="/nutritionist/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Ingredient ingredient;
		
		ingredient = ingredientService.create();
		result= createEditModelAndView(ingredient);
		return result;
	}

	//Edition -------------------------------------
	@RequestMapping(value="/nutritionist/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int ingredientId) {
		
		ModelAndView result;
		Ingredient ingredient;
		
		ingredient = ingredientService.findOne(ingredientId);
		
		Assert.notNull(ingredient);
		result = createEditModelAndView(ingredient);
		return result;
	}
	
	@RequestMapping(value = "/nutritionist/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Ingredient ingredient, BindingResult binding,RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(ingredient);
//						for(ObjectError o: binding.getAllErrors()){
//							System.out.println("ARGUMENT: "+o.getArguments());
//							System.out.println("CODE: "+o.getCode());
//							System.out.println("MESSAGE: "+o.getDefaultMessage());
//						}
//						System.out.println(binding.getAllErrors());
		} else {
			try {
				ingredientService.save(ingredient);
				redirectAttrs.addFlashAttribute("message", "ingredient.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/ingredient/nutritionist/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(ingredient, "ingredient.commit.error");
			}
		}
		return result;
		
	}
	
	@RequestMapping(value="/nutritionist/edit", method= RequestMethod.POST, params="delete")
	public ModelAndView delete(Ingredient ingredient, BindingResult binding){
		ModelAndView result;
	
		try{
			ingredientService.delete(ingredient);
			result= new ModelAndView("redirect:/ingredient/nutritionist/list.do");
		}catch (Throwable oops){
			result= createEditModelAndView(ingredient, "ingredient.commit.error");
		}
		return result;
	}
	
	//Creating ------------------------------------
	@RequestMapping(value="/user/add", method = RequestMethod.GET)
	public ModelAndView addQuantity(@RequestParam int recipeId){
		ModelAndView result;
		IngredientQuantity ingredientQuantity;
		Recipe r;
		
		r = recipeService.findOne(recipeId);
		
		ingredientQuantity = ingredientQuantityService.create();
		ingredientQuantity.setRecipe(r);
		
		result= createEditModelAndView(ingredientQuantity);
		return result;
	}
	
	@RequestMapping(value="/user/edit", method = RequestMethod.GET)
	public ModelAndView editQuantity(@RequestParam int ingredientQuantityId) {
		
		ModelAndView result;
		IngredientQuantity ingredientQuantity;


		ingredientQuantity = ingredientQuantityService.findOne(ingredientQuantityId);	
		Assert.notNull(ingredientQuantity);
		

		
		result = createEditModelAndView(ingredientQuantity);

		return result;
	}
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid IngredientQuantity ingredientQuantity, BindingResult binding,RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(ingredientQuantity);
//						for(ObjectError o: binding.getAllErrors()){
//							System.out.println("ARGUMENT: "+o.getArguments());
//							System.out.println("CODE: "+o.getCode());
//							System.out.println("MESSAGE: "+o.getDefaultMessage());
//						}
//						System.out.println(binding.getAllErrors());
		} else {
			try {
				Assert.isTrue(!ingredientQuantity.getRecipe().getReadOnly());
				ingredientQuantityService.save(ingredientQuantity);
				redirectAttrs.addFlashAttribute("message", "ingredient.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/ingredient/list.do?recipeId="+ingredientQuantity.getRecipe().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(ingredientQuantity, "ingredient.commit.error");
			}
		}
		return result;
		
	}
	
	@RequestMapping(value="/user/edit", method= RequestMethod.POST, params="delete")
	public ModelAndView delete(IngredientQuantity ingredientQuantity, BindingResult binding){
		ModelAndView result;
	
		try{
			Assert.isTrue(!ingredientQuantity.getRecipe().getReadOnly());
			ingredientQuantityService.delete(ingredientQuantity);
			result= new ModelAndView("redirect:/ingredient/list.do?recipeId="+ingredientQuantity.getRecipe().getId());
		}catch (Throwable oops){
			result= createEditModelAndView(ingredientQuantity, "ingredient.commit.error");
		}
		return result;
	}
	
	//Creating ------------------------------------
	@RequestMapping(value="/nutritionist/createProperties", method = RequestMethod.GET)
	public ModelAndView createProperties(){
		ModelAndView result;
		IngredientProperties ingredientProperties;
		
		ingredientProperties = ingredientPropertiesService.create();
		result= createEditModelAndView(ingredientProperties);
		return result;
	}
	
	//Edition -------------------------------------
	@RequestMapping(value="/nutritionist/editProperties", method = RequestMethod.GET)
	public ModelAndView editProperties(@RequestParam int ingredientPropertiesId) {
		
		ModelAndView result;
		IngredientProperties ingredientProperties;
		
		ingredientProperties = ingredientPropertiesService.findOne(ingredientPropertiesId);
		
		Assert.notNull(ingredientProperties);
		result = createEditModelAndView(ingredientProperties);
		return result;
	}
	
	@RequestMapping(value = "/nutritionist/editProperties", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid IngredientProperties ingredientProperties, BindingResult binding,RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(ingredientProperties);
//						for(ObjectError o: binding.getAllErrors()){
//							System.out.println("ARGUMENT: "+o.getArguments());
//							System.out.println("CODE: "+o.getCode());
//							System.out.println("MESSAGE: "+o.getDefaultMessage());
//						}
//						System.out.println(binding.getAllErrors());
		} else {
			try {
				ingredientPropertiesService.save(ingredientProperties);
				redirectAttrs.addFlashAttribute("message", "ingredient.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/ingredient/nutritionist/listProperties.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(ingredientProperties, "ingredient.commit.error");
			}
		}
		return result;
		
	}
	
	@RequestMapping(value="/nutritionist/editProperties", method= RequestMethod.POST, params="delete")
	public ModelAndView delete(IngredientProperties ingredientProperties, BindingResult binding){
		ModelAndView result;
	
		try{
			ingredientPropertiesService.delete(ingredientProperties);
			result= new ModelAndView("redirect:/ingredient/nutritionist/listProperties.do");
		}catch (Throwable oops){
			result= createEditModelAndView(ingredientProperties, "ingredient.commit.error");
		}
		return result;
	}
	
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(Ingredient ingredient) {
		ModelAndView result;
		result = createEditModelAndView(ingredient, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Ingredient ingredient, String message) {
		ModelAndView result;
		
		result = new ModelAndView("ingredient/nutritionist/edit");
		result.addObject("ingredient", ingredient);
		result.addObject("message", message);
		

		return result;
	}
	
	protected ModelAndView createEditModelAndView(IngredientQuantity ingredientQuantity) {
		ModelAndView result;
		result = createEditModelAndView(ingredientQuantity, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(IngredientQuantity ingredientQuantity, String message) {
		ModelAndView result;
		
		Collection<Ingredient> ingredients;
		Collection<Measure> measures;
		Boolean owner = false;
		
		User u = userService.findByPrincipal();		
		if(ingredientQuantity.getRecipe().getUser().equals(u))
			owner=true;
		
		ingredients = ingredientService.findAll();
		measures = measureService.findAll();
		
		ingredients.removeAll(ingredientService.findIngredientsFromRecipe(ingredientQuantity.getRecipe().getId()));
		if(ingredientQuantity.getId()!=0)
			ingredients.add(ingredientQuantity.getIngredient());
		
		result = new ModelAndView("ingredient/user/edit");
		result.addObject("ingredientQuantity", ingredientQuantity);
		result.addObject("message", message);
		if(ingredientQuantity.getId()!=0)
			result.addObject("owner", owner);
		else
			result.addObject("owner", true);
		result.addObject("ingredients", ingredients);
		result.addObject("measures", measures);
		

		return result;
	}
	
	protected ModelAndView createEditModelAndView(IngredientProperties ingredientProperties) {
		ModelAndView result;
		result = createEditModelAndView(ingredientProperties, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(IngredientProperties ingredientProperties, String message) {
		ModelAndView result;
		Collection<Ingredient> ingredients;

		ingredients = ingredientService.findAll();
		
		result = new ModelAndView("ingredient/nutritionist/editProperties");
		result.addObject("ingredientProperties", ingredientProperties);
		result.addObject("ingredients", ingredients);
		result.addObject("message", message);
		

		return result;
	}

}

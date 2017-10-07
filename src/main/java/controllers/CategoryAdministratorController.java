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

import services.CategoryService;
import services.RecipeService;
import domain.Category;
import domain.Recipe;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {
	
	//Services --------------------------------------
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RecipeService recipeService;
	
	//Constructor -----------------------------------
	public CategoryAdministratorController() {
		super();
	}
	
	//Creation --------------------------------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category categoryh;
		
		categoryh = categoryService.create();
		
		result = createEditModelAndView(categoryh);
		
		return result;
	}
	
	//Listing ----------------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categoriesh;
		
		categoriesh = categoryService.findAll();
		
		result = new ModelAndView("category/list");
		result.addObject("categoriesh", categoriesh);
	
		
		return result;
	}
	
	@RequestMapping(value="/listFather", method=RequestMethod.GET)
	public ModelAndView listFather(@RequestParam int categoryId) {
		ModelAndView result;
		Category c;
		
		c = categoryService.findOne(categoryId).getCategoryFather();

		result = new ModelAndView("category/list");
		result.addObject("categoriesh", c);
		
		
		return result;
	}
	
	@RequestMapping(value="/listSon", method=RequestMethod.GET)
	public ModelAndView listSon(@RequestParam int categoryId1) {
		ModelAndView result;
		Collection<Category> c;
		
		c=categoryService.findOne(categoryId1).getCategoriesSons();
		
		
		result = new ModelAndView("category/list");
		result.addObject("categoriesh", c);
		//result.addObject("uri", "category/administrator/list.do");
		
		return result;
	}
	
	//Edition ----------------------------------------
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryId) {
		ModelAndView result;
		Category categoryh;
		

		
		categoryh = categoryService.findOne(categoryId);
		Assert.notNull(categoryh);
		result = createEditModelAndView(categoryh);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Category categoryh, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;

		
		if (binding.hasErrors()) {
			result = createEditModelAndView(categoryh);
		
		} else {
			try {
				categoryService.save(categoryh);
				redirectAttrs.addFlashAttribute("message", "category.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(categoryh, "category.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Category categoryh, BindingResult binding) {
		ModelAndView result;
		Integer categoryId = categoryh.getId();
		Collection<Recipe> recipes = recipeService.showRecipesByCategory(categoryId);
		
		if(recipes.isEmpty()){
			try {
				categoryService.delete(categoryh);
				result = list();
				result.addObject("message", "category.commit.ok");
				result.addObject("msgType", "success");
			} catch (Throwable oops) {
				result = createEditModelAndView(categoryh, "category.commit.error", "danger");
			}
		}else{
			result = createEditModelAndView(categoryh, "category.commit.error", "danger");
		}
		
		return result;
	}
	
	//Ancillary methods ------------------------------
	
		protected ModelAndView createEditModelAndView(Category category) {
			ModelAndView result;
			
			result = createEditModelAndView(category, null, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Category categoryh, String message, String msgType) {
			ModelAndView result;
			Collection<Category> categoriesh= categoryService.findAll();
			categoriesh.remove(categoryh);
			result = new ModelAndView("category/edit");
			result.addObject("categoryh", categoryh);
			result.addObject("message", message);
			result.addObject("msgType", msgType);
			result.addObject("categoriesh", categoriesh);

			return result;
		}

}
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

import services.RecipeService;
import services.StepService;
import services.UserService;
import domain.Recipe;
import domain.Step;
import domain.User;

@Controller
@RequestMapping("/step")
public class StepController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private StepService stepService;
	@Autowired
	private UserService userService;
	@Autowired
	private RecipeService recipeService;

	
	//Constructor ---------------------------
	public StepController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int recipeId) {
		ModelAndView result;
		Collection<Step> steps;
		Recipe recipe;
		boolean owner = false;
		
		recipe = recipeService.findOne(recipeId);
		Assert.notNull(recipe);			
	
		steps = stepService.findStepsOrderByNumber(recipeId);
		Assert.notNull(steps);
		
		try{
			User u = userService.findByPrincipal();
			owner = recipe.getUser().equals(u);
		}
		catch(Throwable oops){
			
		}
		
		result = new ModelAndView("step/list");
		result.addObject("recipeId", recipeId);
		result.addObject("steps", steps);
		result.addObject("owner", owner);
		return result;	
	}
	
	//Creation --------------------------------------
	@RequestMapping(value="/user/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int recipeId) {	
		ModelAndView result;
		Recipe recipe;
		recipe = recipeService.findOne(recipeId);
		Assert.notNull(recipe);
		Step s;
		s = stepService.create();
		s.setRecipe(recipe);
		Integer lastNumberOfStep = stepService.lastNumberOfStep(recipe);
		s.setNumberOfStep(lastNumberOfStep+1);
		
		result = createEditModelAndView(s);
		return result;
	}
	
	//Edition ----------------------------------------
	@RequestMapping(value="/user/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int stepId) {
		ModelAndView result;
		Step s;
		s = stepService.findOne(stepId);
		Assert.notNull(s);
		List<Integer> numberOfSteps = new ArrayList<Integer>();
		numberOfSteps.addAll(stepService.getNumberOfSteps(s.getRecipe()));
		List<Integer> numeros = new ArrayList<Integer>();
		Integer max = stepService.lastNumberOfStep(s.getRecipe());
		for(int i=1;i<max+1;i++){
			if(!numberOfSteps.contains(i))
				numeros.add(i);
		}
		numeros.add(max+1);
		numeros.add(s.getNumberOfStep());
		Collections.sort(numeros);

		result = createEditModelAndView(s);
		result.addObject("numeros", numeros);
		return result;
	}
	
	@RequestMapping(value="/user/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Step step, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
//						for(ObjectError o: binding.getAllErrors()){
//							System.out.println("ARGUMENT: "+o.getArguments());
//							System.out.println("CODE: "+o.getCode());
//							System.out.println("MESSAGE: "+o.getDefaultMessage());
//						}
//						System.out.println(binding.getAllErrors());
			result = createEditModelAndView(step);
		} else {
			try {
				Assert.isTrue(!step.getRecipe().getReadOnly());
				stepService.save(step);
				redirectAttrs.addFlashAttribute("message", "step.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result = new ModelAndView("redirect:/step/list.do?recipeId="+step.getRecipe().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(step, "step.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/user/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Step step, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			Assert.isTrue(!step.getRecipe().getReadOnly());
			stepService.delete(step);
			redirectAttrs.addFlashAttribute("message", "step.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			result = new ModelAndView("redirect:/step/list.do?recipeId="+step.getRecipe().getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(step, "step.commit.error", "danger");
		}
		return result;
	}
	
	//Ancillary methods ------------------------------
	
	protected ModelAndView createEditModelAndView(Step s) {
		ModelAndView result;
		result = createEditModelAndView(s, null, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Step s, String message, String msgType) {
		ModelAndView result;
		boolean owner = false;
		
		User u = userService.findByPrincipal();
		owner = s.getRecipe().getUser().equals(u);
		
		result = new ModelAndView("step/user/edit");
		result.addObject("owner", owner);
		result.addObject("step", s);
		result.addObject("message", message);
		result.addObject("msgType", msgType);

		return result;
	}
		

}
